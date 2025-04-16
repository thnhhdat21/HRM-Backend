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

    @PostMapping("/add-duty")
    private ResponseData<List<DutyResponse>> addDuty(@RequestBody List<DutyRequest> dutyRequest) {
        List<DutyResponse> response = dutyService.createDuty(dutyRequest);
        return ResponseData.<List<DutyResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get list duty successfully")
                .build();
    }

    @PostMapping("/update-duty")
    private ResponseData<DutyResponse> updateDuty(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("des") String des) {
        DutyResponse response = dutyService.updateDuty(id, name, des);
        return ResponseData.<DutyResponse>builder()
                .code(1000)
                .data(response)
                .message("Get list duty successfully")
                .build();
    }

    @PostMapping("/delete-duty")
    private ResponseData<Void> updateDuty(@RequestParam("id") Long id) {
        dutyService.deleteDuty(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete duty successfully")
                .build();
    }
}
