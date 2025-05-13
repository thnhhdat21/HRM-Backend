package vn.tdsoftware.hrm_backend.dto.letter.response.inoutandendwork;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class InOutAndEndWorkResponse {
    private long letterId;
    private int letterState;
    private LocalDate dateRegis;
    private long employeeId;
    private long letterReasonId;
    private String letterReason;
    private int letterType;
    private String description;
}
