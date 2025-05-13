package vn.tdsoftware.hrm_backend.dto.timekeeping.request;

import lombok.Getter;

import java.time.YearMonth;
import java.util.List;

@Getter
public class TimeKeepingClosing {
    List<Long> departments;
    YearMonth yearMonth;
}