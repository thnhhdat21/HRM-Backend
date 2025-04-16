package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.jobposition.request.JobPositionRequest;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionDetailResponse;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionResponse;

import java.util.List;

public interface JobPositionService {
    List<JobPositionResponse> getListJobPosition();
    JobPositionResponse createJobPosition(JobPositionRequest jobPositionRequest);
    JobPositionResponse updateJobPosition(JobPositionRequest jobPositionRequest);
    JobPositionDetailResponse getJobPositionDetail(long id);
    void deleteJobPosition(long id);

}
