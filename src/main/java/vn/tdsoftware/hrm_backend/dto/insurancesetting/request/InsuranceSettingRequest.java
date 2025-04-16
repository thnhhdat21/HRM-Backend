package vn.tdsoftware.hrm_backend.dto.insurancesetting.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceSettingRequest {
    private int id;
    private Integer closingDateIncrease;
    private Boolean singedContract;
    private Boolean returnedFromMaternity;
    private Boolean returnedFromUnpaidLeave;
    private Boolean increasedContribution;
    private Integer closingDateDecrease;
    private Boolean contractTerminated;
    private Boolean maternityLeave;
    private Boolean decreasedContribution;
    private Boolean unpaidLeave;
    private Integer maxUnpaidLeaveDay;
    private Boolean hasUpdate;
}
