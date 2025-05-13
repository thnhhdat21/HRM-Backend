package vn.tdsoftware.hrm_backend.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionResponse;
import vn.tdsoftware.hrm_backend.service.JobPositionService;

import java.util.List;

@RestController
@RequestMapping("/job-position")
@RequiredArgsConstructor
public class JobPositionController {
    private final JobPositionService jobPositionService;
    private final Gson gson;

    @PostMapping("/get-list-job-position")
    public ResponseData<List<JobPositionResponse>> getListJobPosition() {
        List<JobPositionResponse> listResponse = jobPositionService.getListJobPosition();
        return ResponseData.<List<JobPositionResponse>>builder()
                .code(1000)
                .data(listResponse)
                .message("Get list job position successfully")
                .build();
    }

}
