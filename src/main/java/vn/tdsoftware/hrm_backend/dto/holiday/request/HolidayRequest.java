package vn.tdsoftware.hrm_backend.dto.holiday.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HolidayRequest {
    private long id;
    private String reason;
    private int type;
    private LocalDate date;
    private String description;
}
