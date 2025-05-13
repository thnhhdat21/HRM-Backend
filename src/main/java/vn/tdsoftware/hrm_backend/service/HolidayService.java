package vn.tdsoftware.hrm_backend.service;


import vn.tdsoftware.hrm_backend.dto.holiday.request.HolidayFilter;
import vn.tdsoftware.hrm_backend.dto.holiday.request.HolidayRequest;
import vn.tdsoftware.hrm_backend.dto.holiday.response.HolidayResponse;

import java.util.List;

public interface HolidayService {
    List<HolidayResponse> getListHoliday (HolidayFilter filter);
    HolidayResponse updateHoliday(HolidayRequest holidayRequest);
    void deleteHoliday(long holidayId);

}

