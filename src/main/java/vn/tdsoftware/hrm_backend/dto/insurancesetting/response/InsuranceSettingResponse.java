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
    private Boolean singedContract;
    private Boolean returnedLeaveTmp;
    private Boolean leaveTmp;
    private Boolean unpaidLeave;
}
