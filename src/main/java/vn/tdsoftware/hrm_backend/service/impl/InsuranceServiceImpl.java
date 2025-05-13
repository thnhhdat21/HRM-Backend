package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.InsuranceDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.insurance.response.InsuranceResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.InsuranceRepository;
import vn.tdsoftware.hrm_backend.service.InsuranceService;
import vn.tdsoftware.hrm_backend.util.PerInsuranceUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {
    private final InsuranceDAO insuranceDAO;
    private final PerInsuranceUtil perInsuranceUtil;

    @Override
    public List<InsuranceResponse> getListInsurance(EmployeeFilter filter) {
        perInsuranceUtil.checkSameDepartmentByFilter(filter);
        List<InsuranceResponse> listResponse = insuranceDAO.getListInsurance(filter);
        if (listResponse.isEmpty()) {
            throw new BusinessException(ErrorCode.INSURANCE_IS_EMPTY);
        }
        return listResponse;
    }

    @Override
    public int getCountInsurance(EmployeeFilter filter) {
        perInsuranceUtil.checkSameDepartmentByFilter(filter);
        return insuranceDAO.getCountInsurance(filter);
    }
}
