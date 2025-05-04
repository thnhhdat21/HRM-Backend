package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.SalaryTableDAO;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.entity.SalaryTableDetail;

import java.util.List;

@Component
public class SalaryTableDetailDAOImpl extends AbstractDao<SalaryTableDetail> implements SalaryTableDAO {

    @Override
    public List<SalaryDetailResponse> getListSalaryDetail(long salaryTableId) {
        return List.of();
    }
}
