package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.ContractDAO;
import vn.tdsoftware.hrm_backend.dao.WorkDAO;
import vn.tdsoftware.hrm_backend.dto.work.response.ContractResponse;
import vn.tdsoftware.hrm_backend.dto.work.response.WorkResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;
import vn.tdsoftware.hrm_backend.service.ContractService;
import vn.tdsoftware.hrm_backend.service.EmployeeService;
import vn.tdsoftware.hrm_backend.service.ContractService;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractDAO contractDAO;
    private final EmployeeService employeeService;

    @Override
    public ContractResponse getWorkProfile(long employeeId) {
        employeeService.checkEmployeeValidator(employeeId);
        ContractResponse response = contractDAO.getWorkProfile(employeeId);
        if (response == null) {
            throw new BusinessException(ErrorCode.WORK_IS_EMPTY);
        }
        return response;
    }
}
