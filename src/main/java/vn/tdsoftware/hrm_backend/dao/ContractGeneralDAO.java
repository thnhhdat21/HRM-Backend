package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractGeneralDetail;

@Repository
public interface ContractGeneralDAO {
    ContractGeneralDetail getContractGeneralDetail(long employeeId);
}
