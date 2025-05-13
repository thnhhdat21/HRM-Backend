package vn.tdsoftware.hrm_backend.dto.onleave.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OnLeaveResponse {
    private double seniorDay;
    private double regulaDay;
    private double usedDay;
}
