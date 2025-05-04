package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.work.response.WorkResponse;

public interface ContractService {
    WorkResponse getWorkProfile(long employeeId);
}
