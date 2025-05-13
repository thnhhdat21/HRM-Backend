package vn.tdsoftware.hrm_backend.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.LetterDAO;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.entity.TimeKeeping;
import vn.tdsoftware.hrm_backend.entity.TimeKeepingHasLetter;
import vn.tdsoftware.hrm_backend.entity.TimeSheet;
import vn.tdsoftware.hrm_backend.repository.TimeKeepingHasLetterRepository;
import vn.tdsoftware.hrm_backend.repository.TimeKeepingRepository;
import vn.tdsoftware.hrm_backend.service.TimeSheetService;
import vn.tdsoftware.hrm_backend.util.LetterUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LetterScheduler {
    private final LetterDAO letterDAO;
    private final TimeKeepingRepository timeKeepingRepository;
    private final TimeKeepingHasLetterRepository timeKeepingHasLetterRepository;
    private final TimeSheetService timeSheetService;

    @Scheduled(cron = "0 0 0 * * *")
    public void calculationTimekeeping() {
        long start = System.currentTimeMillis();
        System.out.println("start calculationTimekeeping");
        LocalDate today = LocalDate.now();
        List<LeaveLetterResponse> listResponse = letterDAO.getListLeaveLetterApproved(today);
        if (!listResponse.isEmpty()) {
            TimeSheet timeSheet = timeSheetService.getTimeSheet(today.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            for (LeaveLetterResponse response : listResponse) {
                TimeKeeping timeKeeping = new TimeKeeping();
                timeKeeping.setEmployeeId(response.getEmployeeId());
                timeKeeping.setDate(today);
                timeKeeping.setTimeSheetId(timeSheet.getId());
                if (response.getWorkdayEnabled()) {
                    double workDay = LetterUtil.workDayOnLeave(today, response.getDateStart(), response.getDateEnd());
                    timeKeeping.setWorkDay(timeKeeping.getWorkDay() == null ? workDay : timeKeeping.getWorkDay() + workDay);
                }
                TimeKeeping timeKeepingSaved = timeKeepingRepository.save(timeKeeping);
                timeKeepingHasLetterRepository.save(TimeKeepingHasLetter.builder()
                                .timeKeepingId(timeKeepingSaved.getId())
                                .letterId(response.getLetterId())
                        .build());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("end calculationTimekeeping: " + (end - start));
    }
}
