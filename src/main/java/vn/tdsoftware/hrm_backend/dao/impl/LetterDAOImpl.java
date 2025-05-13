package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.LetterDAO;
import vn.tdsoftware.hrm_backend.dto.contract.response.EndJobCurrentDate;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.letter.response.*;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.entity.Letter;
import vn.tdsoftware.hrm_backend.mapper.response.LeaveLetterUnpaidSalaryMapper;
import vn.tdsoftware.hrm_backend.mapper.response.contract.EndJobCurrentDateMapper;
import vn.tdsoftware.hrm_backend.mapper.response.letter.*;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.time.LocalDate;
import java.util.List;

@Component
public class LetterDAOImpl extends AbstractDao<Letter> implements LetterDAO {

    @Override
    public List<LetterResponse> getListLetter(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder(
                "SELECT letterList.createdBy," +
                        "letterList.letterId,  " +
                        "letterList.employeeName, " +
                        "letterList.letterType, " +
                        "letterList.letterState, " +
                        "letterList.createdAt " +
                        "FROM ( " +
                        "SELECT ROW_NUMBER() OVER (ORDER BY letter.createdAt DESC) AS RowNumber," +
                        "letter.id as letterId, " +
                        "ec.fullName as createdBy, " +
                        "employee.fullName as employeeName, " +
                        "letterReason.letterTypeId as letterType, " +
                        "letter.state as letterState, " +
                        "letter.createdAt  " +
                        "from letter  " +
                        "inner join letterReason on letter.reasonId = letterReason.id " +
                        "inner join employee on letter.employeeId = employee.id  " +
                        "inner join employee ec on letter.createdBy = ec.id  " +
                        "inner join contractGeneral  on employee.id = contractGeneral.employeeId " +
                        "inner join department on contractGeneral.departmentId = department.id   " +
                        "inner join jobPosition on contractGeneral.jobPositionId = jobPosition.id " +
                        "inner join duty on jobPosition.dutyId = duty.id " +
                        "where letter.isEnabled = true ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_LETTER));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) AS letterList " +
                "  WHERE letterList.RowNumber BETWEEN ").append((index -1)*12 + 1).append(" AND ").append((index -1)*12 + 12);
        return query(sql.toString(), new LetterResponseMapper());
    }

    @Override
    public List<CountLetterResponse> getCountLetter(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("select " +
                "letterState.id, " +
                "letterState.name, " +
                "COALESCE(letterList.count, 0) as count " +
                "from letterState left join " +
                "(select letter.state, count(letter.id) as count " +
                "from letter " +
                "inner join employee on letter.employeeId = employee.id  " +
                "inner join employee ec on letter.createdBy = ec.id  " +
                "inner join account on employee.id = account.employeeId  " +
                "inner join contractGeneral  on employee.id = contractGeneral.employeeId " +
                "inner join department on contractGeneral.departmentId = department.id   " +
                "inner join jobPosition on contractGeneral.jobPositionId = jobPosition.id " +
                "inner join duty on jobPosition.dutyId = duty.id " +
                "where letter.isEnabled = true ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_DECISION));
        sql.append(" group by letter.state ) AS letterList on letterState.id = letterList.state ");
        return query(sql.toString(), new CountLetterMapper());
    }

    @Override
    public List<OverTimeLetterOfEmployee> getOverTimeLetter(long employeeId, LocalDate startDate, LocalDate endDate) {
        String sql = "select overtimeLetter.dateRegis, " +
                "overtimeLetter.total " +
                "from letter " +
                "inner join overtimeLetter on letter.id = overtimeLetter.letterId " +
                "where letter.isEnabled = 1 and letter.employeeId = " + employeeId +
                " and overtimeLetter.dateRegis between '" + startDate.toString() + "' AND '" + endDate.toString() + "'" ;
        return query(sql, new OverTimeLetterOfEmployeeMapper());
    }

    @Override
    public WorkTimeEmployee getWorkTimeEmployee(LocalDate date, long employeeId) {
        String sql = " select  " +
                "employee.id," +
                "letter.id as letterId,  " +
                "letterreason.goLate, " +
                "letterreason.backEarly  " +
                "from letter " +
                "inner join letterreason on letter.reasonId = letterreason.id  " +
                "inner join worktimeletter on letter.id = worktimeLetter.letterId " +
                "inner join employee on letter.employeeId = employee.id " +
                "where employee.id = ? and '"+ date + "' between worktimeletter.dateStart and worktimeletter.dateEnd and letter.isEnabled = true";
        List<WorkTimeEmployee> list =  query(sql, new WorkTimeEmployeeMapper(), employeeId);
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public List<LeaveLetterResponse> getListLeaveLetterApproved(LocalDate dateCurrent) {
        String sql = "select   " +
                "letter.id as letterId,  " +
                "employee.id as employeeId,   " +
                "letter.state as letterState,   " +
                "letterreason.reason as letterReason,   " +
                "letterreason.letterTypeId as letterType,   " +
                "leaveletter.dateStart,   " +
                "leaveletter.dateEnd,   " +
                "leaveletter.createdAt as dateRegis,  " +
                "letterreason.workDayEnabled,  " +
                "leaveletter.total,  " +
                "letter.description   " +
                "from letter   " +
                "inner join letterreason on letter.reasonId = letterreason.id   " +
                "inner join leaveletter on letter.id = leaveletter.letterId    " +
                "inner join employee on letter.employeeId = employee.id   " +
                "where letter.isEnabled = 1   " +
                "and letter.state =2  " +
                "and '" + dateCurrent + "' between Date(leaveletter.dateStart) and Date(leaveletter.dateEnd) ";
        return query(sql, new LeaveLetterResponseMapper());
    }

    @Override
    public List<EndJobCurrentDate> getListLetterCurrentDate(LocalDate date) {
        String sql = " select  " +
                " contractGeneral.contractOriginal as contractId, " +
                " inoutandendworkletter.dateRegis as currentDate, " +
                " letterReason.reason  " +
                " from letter  " +
                " inner join letterreason on letter.reasonId = letterreason.id  " +
                " inner join inoutandendworkletter on letter.id = inoutandendworkletter.letterId  " +
                " inner join employee on letter.employeeId = employee.id  " +
                " inner join contractgeneral on employee.id = contractgeneral.employeeId " +
                " where letter.isEnabled = 1 " +
                " and letter.state = 2 " +
                " and inoutandendworkletter.dateRegis = '" + date + "'";
        return query(sql, new EndJobCurrentDateMapper());
    }

    @Override
    public List<LeaveLetterUnpaidSalary> getListLeaveLetterUnpaidSalary(LocalDate date, int dateStartOrEnd) {
        StringBuilder sql = new StringBuilder("select  " +
                "letter.employeeId  " +
                "from letter  " +
                "inner join letterreason on letter.reasonId = letterreason.id   " +
                "inner join leaveletter on letter.id = leaveletter.letterId  " +
                "where letter.isEnabled = 1  " +
                "and letter.state = 2  " +
                "and letterreason.workDayEnabled = 0  ");
        if (dateStartOrEnd == 0) {
            sql.append("and Date(leaveletter.dateStart) = '");
        } else
            sql.append("and Date(leaveletter.dateEnd) = '");
        sql.append(date).append("'");
        return query(sql.toString(), new LeaveLetterUnpaidSalaryMapper());
    }
}
