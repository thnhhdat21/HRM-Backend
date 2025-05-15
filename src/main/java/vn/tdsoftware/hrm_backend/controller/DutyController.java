package vn.tdsoftware.hrm_backend.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.duty.request.DutyRequest;
import vn.tdsoftware.hrm_backend.dto.duty.response.DutyResponse;
import vn.tdsoftware.hrm_backend.service.DutyService;

import java.util.List;

@RestController
@RequestMapping("/duty")
@RequiredArgsConstructor
public class DutyController {
    private final DutyService dutyService;
    private final Gson gson;

    @PostMapping("/get-list-duty")
    private ResponseData<List<DutyResponse>> getListDuty() {
        List<DutyResponse> response = dutyService.getListDuty();
        return ResponseData.<List<DutyResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get list duty successfully")
                .build();
    }
}
