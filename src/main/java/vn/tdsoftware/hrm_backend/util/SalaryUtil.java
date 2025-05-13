package vn.tdsoftware.hrm_backend.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class SalaryUtil {
    public static final int PENALTY_GO_LATE = 50000;

    public static int getWorkingDaysInMonth(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                workingDays++;
            }
        }

        return workingDays;
    }


}
