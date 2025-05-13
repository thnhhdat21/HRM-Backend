package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.leavesetting.request.LeaveSettingRequest;
import vn.tdsoftware.hrm_backend.dto.leavesetting.response.LeaveSettingResponse;
import vn.tdsoftware.hrm_backend.service.LeaveSettingService;

@RestController
@RequestMapping("/admin/leave-setting")
@RequiredArgsConstructor
public class LeaveSettingController {
    private final LeaveSettingService leaveSettingService;

    @PostMapping("/get-setting")
    public ResponseData<LeaveSettingResponse> getSetting() {
        LeaveSettingResponse response = leaveSettingService.getSetting();
        return ResponseData.<LeaveSettingResponse>builder()
                .code(1000)
                .data(response)
                .message("Get leave setting successfully")
                .build();
    }

    @PostMapping("/update-setting")
    public ResponseData<LeaveSettingResponse> updateSetting(@RequestBody LeaveSettingRequest request) {
        LeaveSettingResponse response = leaveSettingService.updateSetting(request);
        return ResponseData.<LeaveSettingResponse>builder()
                .code(1000)
                .data(response)
                .message("Update leave setting successfully")
                .build();
    }
}
