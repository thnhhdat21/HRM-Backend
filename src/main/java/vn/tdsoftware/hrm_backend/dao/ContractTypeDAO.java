package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeDetail;

@Repository
public interface ContractTypeDAO {
    ContractTypeDetail getContractType(long id);
    void deleteAllowance(long id);
}
