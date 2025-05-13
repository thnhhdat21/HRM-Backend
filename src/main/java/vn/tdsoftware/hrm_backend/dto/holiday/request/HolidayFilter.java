package vn.tdsoftware.hrm_backend.dto.holiday.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayFilter {
    private long year;
    private int pageIndex;
    private int type;
}
