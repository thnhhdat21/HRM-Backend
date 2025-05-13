package vn.tdsoftware.hrm_backend.dto.holiday.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class HolidayResponse {
    private long id;
    private String reason;
    private int type;
    private LocalDate date;
    private String description;
}
