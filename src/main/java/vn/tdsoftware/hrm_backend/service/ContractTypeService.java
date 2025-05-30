package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.contracttype.request.ContractTypeRequest;
import vn.tdsoftware.hrm_backend.dto.contracttype.request.ContractTypeUpdate;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeDetail;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;

import java.util.List;

public interface ContractTypeService {
    List<ContractTypeResponse> getListContractType();
    ContractTypeResponse createContractType(ContractTypeRequest typeContractRequest);
    ContractTypeDetail getContractType(long id);
    ContractTypeResponse updateContractType(ContractTypeUpdate typeContractRequest);
    void deleteContractType(long id);
}
