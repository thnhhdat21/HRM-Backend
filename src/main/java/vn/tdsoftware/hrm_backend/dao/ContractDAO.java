package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.contract.response.*;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractDAO {
    WorkProfileResponse getWorkProfile(long employeeId);
    List<WorkProcessResponse> getWorkProcess (long employeeId);
    ContractProfileResponse getContractProfileByEmployee(long employeeId);
    ContractProfileResponse getContractProfileByContractId(long contractId);
    ContractDetailResponse getContractDetail(long contractId);
    List<ContractOfEmployeeResponse> getListContractOfEmployee(long employeeId);
    List<ContractResponse> getListContract(EmployeeFilter filter);
    int countContractAppendix(long contractId);
    List<CountContractTypeResponse> getCountContractType(EmployeeFilter filter);

}
