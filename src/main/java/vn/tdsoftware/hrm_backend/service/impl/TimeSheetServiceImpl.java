package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.entity.TimeSheet;
import vn.tdsoftware.hrm_backend.repository.TimeSheetRepository;
import vn.tdsoftware.hrm_backend.service.TimeSheetService;

@Service
@RequiredArgsConstructor
public class TimeSheetServiceImpl implements TimeSheetService {
    private final TimeSheetRepository timeSheetRepository;

    @Override
    public TimeSheet getTimeSheet(String yearMonth) {
        TimeSheet timeSheet = timeSheetRepository.findByYearMonthAndIsEnabled(yearMonth, true).orElse(null);
        if (timeSheet == null) {
            timeSheet = new TimeSheet();
            timeSheet.setYearMonth(yearMonth);
            timeSheet = timeSheetRepository.save(timeSheet);
        }
        return timeSheet;
    }
}
