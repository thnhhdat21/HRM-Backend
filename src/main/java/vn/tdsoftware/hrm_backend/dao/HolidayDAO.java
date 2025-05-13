package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.holiday.response.HolidayResponse;

import java.util.List;

@Repository
public interface HolidayDAO {
    List<HolidayResponse> getListHoliday(long year,int pageIndex, int type);
}
