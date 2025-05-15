package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeDetail;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;

import java.util.List;

@Repository
public interface ContractTypeDAO {
    ContractTypeDetail getContractType(long id);
    void deleteAllowance(long id);
}
