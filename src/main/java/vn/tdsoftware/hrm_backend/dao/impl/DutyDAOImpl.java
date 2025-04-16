package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.DutyDAO;
import vn.tdsoftware.hrm_backend.dto.duty.response.DutyResponse;
import vn.tdsoftware.hrm_backend.entity.Duty;
import vn.tdsoftware.hrm_backend.mapper.response.duty.DutyResponseMapper;

import java.util.List;

@Component
public class DutyDAOImpl extends AbstractDao<Duty> implements DutyDAO {


    @Override
    public List<DutyResponse> getListDuty() {
        String sql = "select duty.id, duty.name , duty.description, duty.status, nv1.fullName as createdBy " +
                "from duty left join employee nv1 on duty.createdBy = nv1.id " +
                "where duty.isEnabled = true;";
        List<DutyResponse> listDutyResponse = query(sql, new DutyResponseMapper());
        return listDutyResponse;
    }

    @Override
    public void deleteDuty(long id) {
        String sqlQuery = "update duty " +
                "set isEnabled = 0 " +
                "where id  = ? ";
        update(sqlQuery, id);
    }
}
