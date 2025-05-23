package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;

import java.util.List;

@Repository
public interface AllowanceDAO {
    List<AllowanceResponse> getAllowanceByContractType(long contractTypeId);
}
