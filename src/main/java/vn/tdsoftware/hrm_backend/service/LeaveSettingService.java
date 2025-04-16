package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.leavesetting.request.LeaveSettingRequest;
import vn.tdsoftware.hrm_backend.dto.leavesetting.response.LeaveSettingResponse;

public interface LeaveSettingService {
    LeaveSettingResponse getSetting();
    LeaveSettingResponse updateSetting(LeaveSettingRequest leaveSettingRequest);
}
