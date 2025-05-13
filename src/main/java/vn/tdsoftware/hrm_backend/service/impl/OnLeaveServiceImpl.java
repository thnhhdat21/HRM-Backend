package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.OnLeaveDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.onleave.request.OnLeaveRequest;
import vn.tdsoftware.hrm_backend.dto.onleave.response.EmployeeOnLeaveResponse;
import vn.tdsoftware.hrm_backend.dto.onleave.response.OnLeaveResponse;
import vn.tdsoftware.hrm_backend.entity.OnLeave;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.OnLeaveMapper;
import vn.tdsoftware.hrm_backend.repository.OnLeaveRepository;
import vn.tdsoftware.hrm_backend.service.EmployeeService;
import vn.tdsoftware.hrm_backend.service.OnLeaveService;
import vn.tdsoftware.hrm_backend.util.PerEmployeeUtil;
import vn.tdsoftware.hrm_backend.util.PerTimeSheetUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OnLeaveServiceImpl implements OnLeaveService {
    private final OnLeaveRepository onleaveRepository;
    private final EmployeeService employeeService;
    private final OnLeaveDAO onLeaveDAO;
    private final PerEmployeeUtil perEmployeeUtil;
    private final PerTimeSheetUtil perTimeSheetUtil;

    @Override
    public OnLeaveResponse getOnLeaveProfile(long employeeId) {
        perEmployeeUtil.checkWatchSameDepartmentByEmployeeId(employeeId);
        employeeService.checkEmployeeValidator(employeeId);
        int currentYear = LocalDate.now().getYear();
        OnLeave onLeave = onleaveRepository.findByEmployeeIdAndYearAndIsEnabled(employeeId, currentYear, true).orElseThrow(
                () -> new BusinessException(ErrorCode.ON_LEAVE_IS_EMPTY)
        );
        return OnLeaveMapper.mapToOnLeaveResponse(onLeave);
    }

    @Override
    public void updateOnLeaveProfile(OnLeaveRequest request) {
        perEmployeeUtil.checkUpdateSameDepartmentByEmployeeId(request.getEmployeeId());
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        int currentYear = LocalDate.now().getYear();
        OnLeave onLeave = onleaveRepository.findByEmployeeIdAndYearAndIsEnabled(request.getEmployeeId(), currentYear, true).orElseThrow(
                () -> new BusinessException(ErrorCode.ON_LEAVE_IS_EMPTY)
        );
        if (request.getRegulaDay() <= 0 || request.getUsedDay() <=0 || request.getUsedDay() > request.getRegulaDay()) {
            throw new BusinessException(ErrorCode.TIME_ON_LEAVE_INVALID);
        }
        onLeave.setSeniorDay(request.getSeniorDay());
        onLeave.setRegulaDay(request.getRegulaDay());
        onLeave.setUsedDay(request.getUsedDay());
        onleaveRepository.save(onLeave);
    }

    @Override
    public List<EmployeeOnLeaveResponse> getEmployeeOnLeave(EmployeeFilter filter) {
        perTimeSheetUtil.checkSameDepartmentByFilter(filter);
        List<EmployeeOnLeaveResponse> responses = onLeaveDAO.getEmployeeOnLeave(filter);
        if (responses.isEmpty()) {
            throw new BusinessException(ErrorCode.ON_LEAVE_IS_EMPTY);
        }
        return responses;
    }

    @Override
    public int getCountEmployee(EmployeeFilter filter) {
        perTimeSheetUtil.checkSameDepartmentByFilter(filter);
        return onLeaveDAO.getCountEmployee(filter);
    }
}
