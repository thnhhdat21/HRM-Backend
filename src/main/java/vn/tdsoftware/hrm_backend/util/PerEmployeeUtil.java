package vn.tdsoftware.hrm_backend.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.DepartmentDAO;
import vn.tdsoftware.hrm_backend.dto.account.response.CurrentAccountDTO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PerEmployeeUtil {
    private final DepartmentDAO departmentDAO;

    public void checkSameDepartmentByFilter(EmployeeFilter filter) {
        if (CurrentAccountDTO.getPermission().contains("ROLE_WATCH_EMPLOYEE_DEPARTMENT")) {
            if ((!filter.getDepartment().isEmpty() &&
                    (filter.getDepartment().size() > 1 || !Objects.equals(filter.getDepartment().get(0), CurrentAccountDTO.getDepartmentId())))) {
                throw new BusinessException(ErrorCode.NO_PERMISSION_DEPARTMENT_OTHER);
            }
            filter.setDepartment(List.of(CurrentAccountDTO.getDepartmentId()));
        }
    }

    public void checkSameEmployee(Long employeeId) {
        if (!Objects.equals(CurrentAccountDTO.getEmployeeId(), employeeId)) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_MANAGE_EMPLOYEE_OTHER);
        }
    }

    public void checkWatchSameDepartmentByEmployeeId(Long employeeId) {
        checkRoleWatch(employeeId);
    }

    public void checkUpdateSameDepartmentByEmployeeId(Long employeeId) {
        checkRoleUpdate(employeeId);
    }

    public void checkManageOrCreateEmployee(long itemId) {
        if (itemId == 0 && !CurrentAccountDTO.getPermission().contains("ROLE_CREATE_EMPLOYEE")) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_CREATE);
        } else if (itemId != 0 && !CurrentAccountDTO.getPermission().contains("ROLE_MANAGE_EMPLOYEE")) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_MANAGE);
        }
    }

    private void checkRoleWatch(long employeeId) {
        Set<String> roles = CurrentAccountDTO.getPermission();
        Long currentEmployeeId = CurrentAccountDTO.getEmployeeId();
        Long currentDepartmentId = CurrentAccountDTO.getDepartmentId();

        // Nếu có quyền xem toàn công ty → luôn được phép
        if (roles.contains("ROLE_WATCH_EMPLOYEE_COMPANY") || roles.contains("ADMIN")) return;

        // Nếu đang xem chính mình → luôn được phép
        if (Objects.equals(currentEmployeeId, employeeId)) return;

        // Nếu có quyền xem cùng phòng ban → kiểm tra phòng ban
        if (roles.contains("ROLE_WATCH_EMPLOYEE_DEPARTMENT")) {
            Long targetDepartmentId = departmentDAO.getDepartmentByEmployeeId(employeeId);
            if (!Objects.equals(currentDepartmentId, targetDepartmentId)) {
                throw new BusinessException(ErrorCode.NO_PERMISSION_DEPARTMENT_OTHER);
            }
            return;
        }

        // Nếu chỉ có quyền xem bản thân → không cho phép xem người khác
        if (roles.contains("ROLE_WATCH_SELF_EMPLOYEE")) {
            throw new BusinessException(ErrorCode.NO_PERMISSION_WATCH_EMPLOYEE_OTHER);
        }

        // Không có quyền hợp lệ nào
        throw new BusinessException(ErrorCode.NO_PERMISSION_MANAGE);
    }

    private void checkRoleUpdate(long employeeId) {
        Set<String> roles = CurrentAccountDTO.getPermission();
        Long currentEmployeeId = CurrentAccountDTO.getEmployeeId();
        Long currentDepartmentId = CurrentAccountDTO.getDepartmentId();

        // Nếu có quyền xem toàn công ty → luôn được phép
        if (roles.contains("ROLE_MANAGE_EMPLOYEE")) return;

        // Nếu đang xem chính mình → luôn được phép
        if (Objects.equals(currentEmployeeId, employeeId)) return;

        // Nếu có quyền xem cùng phòng ban → kiểm tra phòng ban
        if (roles.contains("ROLE_MANAGE_SELF_EMPLOYEE")) {
            Long targetDepartmentId = departmentDAO.getDepartmentByEmployeeId(employeeId);
            if (!Objects.equals(currentDepartmentId, targetDepartmentId)) {
                throw new BusinessException(ErrorCode.NO_PERMISSION_DEPARTMENT_OTHER);
            }
        }
    }
}
