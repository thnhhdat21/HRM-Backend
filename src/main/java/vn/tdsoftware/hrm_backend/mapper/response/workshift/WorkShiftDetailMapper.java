package vn.tdsoftware.hrm_backend.mapper.response.workshift;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftDetail;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;
import vn.tdsoftware.hrm_backend.util.ConvertUtil;

import java.sql.ResultSet;
import java.util.List;

public class WorkShiftDetailMapper implements RowMapper<WorkShiftDetail> {
    @Override
    public WorkShiftDetail mapRow(ResultSet resultSet) {
        try {
            WorkShiftDetail response = new WorkShiftDetail();
            response.setId(resultSet.getLong("id"));
            response.setCode(resultSet.getString("code"));
            response.setName(resultSet.getString("name"));
            response.setTimeIn(resultSet.getTime("timeIn").toLocalTime());
            response.setTimeOut(resultSet.getTime("timeOut").toLocalTime());
            response.setNextDayEnabled(resultSet.getBoolean("nextDayEnabled"));
            response.setBreakStartTime(resultSet.getTime("breakStartTime").toLocalTime());
            response.setBreakEndTime(resultSet.getTime("breakEndTime").toLocalTime());
            response.setTotalTime(resultSet.getDouble("totalTime"));
            response.setTotalWorkDay(resultSet.getDouble("totalWorkDay"));
            response.setCheckinFirst(resultSet.getTime("checkinFirst").toLocalTime());
            response.setCheckoutLater(resultSet.getTime("checkoutLater").toLocalTime());
            response.setAutoTimeKeeping(resultSet.getBoolean("autoTimeKeeping"));
            response.setAutoCheckoutForPosition(resultSet.getBoolean("autoCheckoutForPosition"));

            String listJob = resultSet.getString("jobPositions");
            if (listJob != null) {
                List<Long> jobPositions = ConvertUtil.convertStringToListLong(listJob);
                response.setJobPositions(jobPositions);
            }
            return response;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
