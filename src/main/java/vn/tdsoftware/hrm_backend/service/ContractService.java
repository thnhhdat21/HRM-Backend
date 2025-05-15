package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.contract.request.ContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.request.EndContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.response.*;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.entity.Contract;

import java.util.List;

public interface ContractService {
    WorkProfileResponse getWorkProfile(long employeeId);
    List<WorkProcessResponse> getWorkProcess(long employeeId);
    ContractProfileResponse getContractProfileByEmployee(long employeeId);
    ContractProfileResponse getContractProfileByContractId(long contractId);
    ContractDetailResponse getContractDetail(long contractId);
    void updateContract(ContractRequest contractRequest);
    void createContract(ContractRequest contractRequest);
    List<ContractOfEmployeeResponse> getListContractOfEmployee(long employeeId);
    List<ContractResponse> getListContract(EmployeeFilter filter);
    List<CountContractTypeResponse> getCountContractType(EmployeeFilter filter);
    void endContract(EndContractRequest request);
    int countContractAppendix(long contractId);
    void expireContract(long contractId, boolean contractDisabled);
    void activeContract(long employeeId, Contract contract);
}
