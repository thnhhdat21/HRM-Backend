package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.work.response.WorkResponse;
import vn.tdsoftware.hrm_backend.service.WorkService;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class ContractController {
    private final WorkService workService;

    @PostMapping("/get-work-profile-employee")
    public ResponseData<WorkResponse> getWorkProfile(@RequestParam("id") long id) {
        WorkResponse response = workService.getWorkProfile(id);
        return ResponseData.<WorkResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }
}
