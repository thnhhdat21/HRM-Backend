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
public class PerTimeSheetUtil {
    private final DepartmentDAO departmentDAO;

    public void checkWatchSameDepartmentByEmployeeId(Long employeeId) {
        Set<String> roles = CurrentAccountDTO.getPermission();
        Long currentEmployeeId = CurrentAccountDTO.getEmployeeId();
        Long currentDepartmentId = CurrentAccountDTO.getDepartmentId();

        // Nếu có quyền xem toàn công ty → luôn được phép
        if (roles.contains("ROLE_WATCH_TIMESHEET_COMPANY") || roles.contains("ADMIN")) return;

        // Nếu đang xem chính mình → luôn được phép
        if (Objects.equals(currentEmployeeId, employeeId)) return;

        if (roles.contains("ROLE_WATCH_TIMESHEET_DEPARTMENT")) {
            Long targetDepartmentId = departmentDAO.getDepartmentByEmployeeId(employeeId);
            if (!Objects.equals(currentDepartmentId, targetDepartmentId)) {
                throw new BusinessException(ErrorCode.NO_PERMISSION_DEPARTMENT_OTHER);
            }
        }
    }

    public void checkSameDepartmentByFilter(EmployeeFilter filter) {
        if (CurrentAccountDTO.getPermission().contains("ROLE_WATCH_TIMESHEET_DEPARTMENT")) {
            if ((!filter.getDepartment().isEmpty() &&
                    (filter.getDepartment().size() > 1 || !Objects.equals(filter.getDepartment().get(0), CurrentAccountDTO.getDepartmentId())))) {
                throw new BusinessException(ErrorCode.NO_PERMISSION_DEPARTMENT_OTHER);
            }
            filter.setDepartment(List.of(CurrentAccountDTO.getDepartmentId()));
        }
    }
}
