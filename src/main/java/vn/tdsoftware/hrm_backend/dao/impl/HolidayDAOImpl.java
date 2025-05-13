package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.HolidayDAO;
import vn.tdsoftware.hrm_backend.dto.holiday.response.HolidayResponse;
import vn.tdsoftware.hrm_backend.entity.Holiday;
import vn.tdsoftware.hrm_backend.mapper.response.holiday.HolidayResponseMapper;

import java.util.List;

@Component
public class HolidayDAOImpl extends AbstractDao<Holiday> implements HolidayDAO {

    @Override
    public List<HolidayResponse> getListHoliday(long year,int pageIndex, int type) {
        StringBuilder sql = new StringBuilder(
                "SELECT holidayList.holidayId, " +
                " holidayList.reason, " +
                " holidayList.type, " +
                " holidayList.date," +
                " holidayList.description " +
                "FROM ( " +
                "SELECT ROW_NUMBER() OVER () AS RowNumber, " +
                " holiday.id as holidayId, " +
                " holiday.reason, " +
                " holiday.type, " +
                " holiday.date, " +
                " holiday.description " +
                " from holiday  " +
                " where holiday.isEnabled = 1 " );
        if(type != 0)
            sql.append(" and holiday.type = ").append(type);
        if (year != 0) {
            sql.append(" and Year(holiday.date) = ").append(year);
        }
        int index = pageIndex == 0 ? 1 : pageIndex;
        sql.append(" ) AS holidayList " +
                "  WHERE holidayList.RowNumber BETWEEN ").append((index-1) * 12 + 1).append(" AND ").append((index)*12);
        return query(sql.toString(), new HolidayResponseMapper());
    }
}
