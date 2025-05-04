package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.work.response.ContractResponse;
import vn.tdsoftware.hrm_backend.dto.work.response.WorkResponse;

@Repository
public interface ContractDAO {
    ContractResponse getWorkProfile(long employeeId);
}
