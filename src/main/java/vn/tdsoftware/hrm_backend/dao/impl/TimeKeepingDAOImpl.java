package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.TimeKeepingDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.timekeeping.request.WorkingDayRequest;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeCountTimeKeeping;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeeping;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.WorkingDayResponse;
import vn.tdsoftware.hrm_backend.entity.TimeKeeping;
import vn.tdsoftware.hrm_backend.mapper.response.timekeeping.EmployeeCountTimeKeepingMapper;
import vn.tdsoftware.hrm_backend.mapper.response.timekeeping.EmployeeTimeKeepingMapper;
import vn.tdsoftware.hrm_backend.mapper.response.timekeeping.WorkingDayResponseMapper;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Component
public class TimeKeepingDAOImpl extends AbstractDao<TimeKeeping> implements TimeKeepingDAO {

    @Override
    public List<EmployeeTimeKeeping> getListTimeKeeping(EmployeeFilter filter, LocalDate dateStart, LocalDate dateEnd) {
        StringBuilder sql = new StringBuilder(" WITH employeeList AS ( " +
                "SELECT  " +
                "ROW_NUMBER() OVER (ORDER BY department.departmentLevel ASC, department.name DESC, employee.fullName ASC) AS RowNumber,  " +
                "employee.id, employee.employeeCode as code, employee.fullName, " +
                "department.name as department, " +
                "jobPosition.name as jobPosition, " +
                "(IFNULL( onLeave.regulaDay, 0) + IFNULL( onLeave.seniorDay, 0)) as totalDay, " +
                "onLeave.usedDay, " +
                "overtime.overTimeTotal " +
                "FROM employee " +
                "INNER JOIN contractGeneral ON employee.id = contractGeneral.employeeId " +
                "INNER JOIN department ON contractGeneral.departmentId = department.id " +
                "INNER JOIN jobPosition ON contractGeneral.jobPositionId = jobPosition.id " +
                "LEFT JOIN onLeave ON employee.id = onLeave.employeeId AND onLeave.year = Year('").append(dateStart).append("') " +
                "LEFT JOIN ( " +
                "SELECT letter.employeeId, SUM( overTimeLetter.total) as overTimeTotal " +
                "FROM letter " +
                "INNER JOIN  overTimeLetter ON letter.id =  overTimeLetter.letterId " +
                "WHERE letter.state = 2 AND letter.isEnabled = true " +
                "AND overTimeLetter.dateRegis BETWEEN '").append(dateStart).append("' AND '").append(dateEnd).append("' " +
                "GROUP BY letter.employeeId " +
                ") overtime ON employee.id = overtime.employeeId " +
                "WHERE employee.isEnabled = true AND department.departmentLevel > 2 ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) " +
                "SELECT " +
                "employeeList.id as employeeId, " +
                "employeeList.code as employeeCode, " +
                "employeeList.fullName as employeeName, " +
                "employeeList.department, " +
                "employeeList.jobPosition, " +
                "employeeList.totalDay as onLeaveTotal, " +
                "employeeList.usedDay as onLeaveUsed, " +
                "employeeList.overTimeTotal, " +
                "timeKeeping.date as dateWorking, " +
                "timeKeeping.workDay, " +
                "timeKeeping.isLate, " +
                "letterList.symbolLetter " +
                "FROM employeeList " +
                "LEFT JOIN timeKeeping  ON employeeList.id = timeKeeping.employeeId AND timeKeeping.date BETWEEN '").append(dateStart).append("' AND '").append(dateEnd).append("' " +
                "LEFT JOIN ( " +
                "SELECT timeKeeping.id as timekeepingId, GROUP_CONCAT(concat(letterReason.symbol, '-', letterReason.letterTypeId)) as symbolLetter " +
                "FROM timeKeeping  " +
                "INNER JOIN  timeKeepingHasLetter  ON timeKeeping.id =  timeKeepingHasLetter.timekeepingId " +
                "INNER JOIN letter ON  timeKeepingHasLetter.letterId = letter.id " +
                "INNER JOIN letterReason ON letter.reasonId = letterReason.id " +
                "GROUP BY timeKeeping.id " +
                ") letterList ON timeKeeping.id = letterList.timekeepingId " +
                "  WHERE employeeList.RowNumber BETWEEN ").append((index-1) * 12 + 1).append(" AND ").append((index)*12)
                .append(" ORDER BY employeeList.RowNumber, timeKeeping.date ");
        return query(sql.toString(), new EmployeeTimeKeepingMapper(), dateStart, dateEnd);
    }

    @Override
    public int getCountTimeKeeping(EmployeeFilter filter) {
        String sql = "SELECT COUNT(DISTINCT employee.id) " +
                "FROM employee " +
                "INNER JOIN contractGeneral ON employee.id = contractGeneral.employeeId " +
                "INNER JOIN department ON contractGeneral.departmentId = department.id " +
                "INNER JOIN jobPosition ON contractGeneral.jobPositionId = jobPosition.id " +
                "WHERE employee.isEnabled = true AND department.departmentLevel > 2 " + SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE);
        return count(sql);
    }


    @Override
    public void closingTimeKeeping(YearMonth yearMonth) {
        String sql = "Update timeSheet " +
                "set isClosed = 1 " +
                "where timeSheet.yearMonth = '" + yearMonth + "' ";
        update(sql);
    }

    @Override
    public WorkingDayResponse getWorkingDay(WorkingDayRequest request) {
        String sql = "select timeKeeping.employeeId, timeIn, timeOut, timeLate, timeEarly, timeKeeping.isLate, timeKeeping.workDay, " +
                "GROUP_CONCAT(letter.id) as letterIds " +
                "from timeKeeping " +
                "left join timeKeepingHasLetter on timeKeeping.id = timeKeepingHasLetter.timeKeepingId " +
                "left join letter on timeKeepingHasLetter.letterId = letter.id " +
                "where timeKeeping.employeeId = ? and timeKeeping.date = '" +
                request.getWorkingDay() + "' and timeKeeping.isEnabled = true " +
                "GROUP BY timeKeeping.employeeId, timeIn, timeOut, timeLate, timeEarly,timeKeeping.isLate, timeKeeping.workDay ";
        List<WorkingDayResponse> response = query(sql, new WorkingDayResponseMapper(), request.getEmployeeId());
        return response.isEmpty() ? new WorkingDayResponse(): response.get(0);
    }

    @Override
    public List<EmployeeCountTimeKeeping> getListEmployeeTimeKeeping(int workDayReal, String yearMonth) {
        String sql = " select employeeId, sum(timekeeping.workday) as workday " +
                " from timekeeping inner join timesheet on timekeeping.timeSheetId = timesheet.id " +
                " where timeSheet.yearMonth = '" + yearMonth + "' " +
                " group by employeeId " +
                " having workday < " + (workDayReal - 14);
        return query(sql, new EmployeeCountTimeKeepingMapper());
    }
}
