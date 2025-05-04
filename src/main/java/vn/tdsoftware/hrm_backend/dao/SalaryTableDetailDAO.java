package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;

import java.util.List;

@Repository
public interface SalaryTableDetailDAO {
    List<SalaryDetailResponse> getListSalaryDetail(long salaryTableId);

}
