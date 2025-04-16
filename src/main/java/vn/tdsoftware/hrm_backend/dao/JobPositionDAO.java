package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionResponse;

import java.util.List;

@Repository
public interface JobPositionDAO {
    List<JobPositionResponse> getListJobPosition();
    void deleteJobPosition(long id);
}
