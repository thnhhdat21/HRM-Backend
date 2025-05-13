package vn.tdsoftware.hrm_backend.dto.insurancesetting.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceSettingRequest {
    private int id;
    private Boolean singedContract;
    private Boolean returnedLeaveTmp;
    private Boolean leaveTmp;
    private Boolean unpaidLeave;
    private Boolean hasUpdate;
}
