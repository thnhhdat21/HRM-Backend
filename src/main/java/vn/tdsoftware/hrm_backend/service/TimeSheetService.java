package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.entity.TimeSheet;

public interface TimeSheetService {
    TimeSheet getTimeSheet(String yearMonth);
}
