package vn.tdsoftware.hrm_backend.dto.letter.request.inoutandendword;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InOutAndEndWorkRequest {
    private long letterId;
    private long employeeId;
    private long letterReasonId;
    private LocalDate dateRegis;
    private String description;
    private int type;
}
