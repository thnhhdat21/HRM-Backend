package vn.tdsoftware.hrm_backend.dto.contract.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EndJobCurrentDate {
    private long contractId;
    private LocalDate currentDate;
    private String reason;
}
