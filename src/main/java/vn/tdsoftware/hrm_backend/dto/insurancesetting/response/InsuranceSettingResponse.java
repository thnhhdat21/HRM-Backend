package vn.tdsoftware.hrm_backend.dto.insurancesetting.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsuranceSettingResponse {
    private Integer id;
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
}
