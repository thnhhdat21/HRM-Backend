package vn.tdsoftware.hrm_backend.controller.manage;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.jobposition.request.JobPositionRequest;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionDetailResponse;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionResponse;
import vn.tdsoftware.hrm_backend.service.JobPositionService;

@RestController
@RequestMapping("/admin/job-position")
@RequiredArgsConstructor
public class ManageJobPositionController {
    private final JobPositionService jobPositionService;
    private final Gson gson;

    @PostMapping("/create-job-position")
    public ResponseData<JobPositionResponse> createJobPosition(@RequestBody JobPositionRequest request) {
        System.out.println(gson.toJson(request));
        JobPositionResponse response = jobPositionService.createJobPosition(request);
        return ResponseData.<JobPositionResponse>builder()
                .code(1000)
                .data(response)
                .message("Create job position successfully")
                .build();
    }

    @PostMapping("/update-job-position")
    public ResponseData<JobPositionResponse> updateJobPosition(@RequestBody JobPositionRequest request) {
        JobPositionResponse response = jobPositionService.updateJobPosition(request);
        return ResponseData.<JobPositionResponse>builder()
                .code(1000)
                .data(response)
                .message("Update job position successfully")
                .build();
    }

    @PostMapping("/get-job-position-detail")
    public ResponseData<JobPositionDetailResponse> updateJobPosition(@RequestParam long id) {
        JobPositionDetailResponse response = jobPositionService.getJobPositionDetail(id);
        return ResponseData.<JobPositionDetailResponse>builder()
                .code(1000)
                .data(response)
                .message("get job position detail successfully")
                .build();
    }

    @PostMapping("/delete-job-position")
    public ResponseData<Void> deleteJobPosition(@RequestParam long id) {
        jobPositionService.deleteJobPosition(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete job position successfully")
                .build();
    }
}
