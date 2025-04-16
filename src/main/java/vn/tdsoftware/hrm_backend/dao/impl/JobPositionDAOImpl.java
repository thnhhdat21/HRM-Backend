package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.JobPositionDAO;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionResponse;
import vn.tdsoftware.hrm_backend.entity.JobPosition;
import vn.tdsoftware.hrm_backend.mapper.response.jobposition.JobPositionResponseMapper;

import java.util.List;

@Component
public class JobPositionDAOImpl extends AbstractDao<JobPosition> implements JobPositionDAO {
    @Override
    public List<JobPositionResponse> getListJobPosition() {
        String sqlQuery = "select jobPosition.id, " +
                "jobPosition.name, " +
                "role.name as role, " +
                "jobPosition.salaryFrom, " +
                "jobPosition.salaryTo, " +
                "jobPosition.status, " +
                "nv1.fullName as createBy," +
                "jobPosition.description as des " +
                "from jobPosition inner join role on jobPosition.roleId = role.id " +
                "left join employee nv1 on jobPosition.createdBy = nv1.id " +
                "where jobPosition.isEnabled = true";
        List<JobPositionResponse> response = query(sqlQuery, new JobPositionResponseMapper());
        return response;
    }

    @Override
    public void deleteJobPosition(long id) {
        String sql = "update jobPosition " +
                "set isEnabled = false " +
                "where id = ?";
        update(sql, id);
    }
}
