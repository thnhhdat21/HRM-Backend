package vn.tdsoftware.hrm_backend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LetterUtil {

    public static double workDayOnLeave(LocalDate today, LocalDateTime start, LocalDateTime end) {
        if (today.isEqual(start.toLocalDate()) && today.isEqual(end.toLocalDate())) {
            LocalTime timeStart = start.toLocalTime();
            LocalTime timeEnd = end.toLocalTime();
            if (timeStart.isAfter(LocalTime.NOON) && timeEnd.isBefore(LocalTime.NOON)) return 1.0;
            return 0.5;
        } else if (today.isEqual(start.toLocalDate())) {
            return start.toLocalTime().isBefore(LocalTime.NOON) ? 1 : 0.5;
        } else if (today.isEqual(end.toLocalDate())) {
            return end.toLocalTime().isAfter(LocalTime.NOON) ? 1 : 0.5;
        } else {
            return 1;
        }
    }
}
