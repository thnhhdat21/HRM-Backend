package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.allowance.request.AllowanceRequest;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;

import java.util.List;

public interface AllowanceService {
    List<AllowanceResponse> getListAllowance();
    List<AllowanceResponse> createAllowance(List<AllowanceRequest> allowanceRequest);
    AllowanceResponse updateAllowance(AllowanceRequest allowanceRequest);
    void deleteAllowance(long id);
    List<AllowanceResponse> getAllowanceByContractType(long contractTypeId);
}
