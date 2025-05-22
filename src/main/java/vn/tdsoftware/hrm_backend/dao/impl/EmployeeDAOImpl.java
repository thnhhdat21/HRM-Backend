package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.EmployeeDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeTimeSheetRequest;
import vn.tdsoftware.hrm_backend.dto.employee.response.*;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.overtime.OverTimeLetterResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryMonth;
import vn.tdsoftware.hrm_backend.entity.Employee;
import vn.tdsoftware.hrm_backend.mapper.response.EmployeeSelectMapper;
import vn.tdsoftware.hrm_backend.mapper.response.employee.*;
import vn.tdsoftware.hrm_backend.mapper.response.letter.LeaveLetterResponseMapper;
import vn.tdsoftware.hrm_backend.mapper.response.letter.OverTimeLetterResponseMapper;
import vn.tdsoftware.hrm_backend.mapper.response.salary.SalaryDetailResponseMapper;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Component
public class EmployeeDAOImpl extends AbstractDao<Employee> implements EmployeeDAO {

    @Override
    public List<EmployeeResponse> getListEmployeeFilter(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT " +
            "employeeList.id," +
            "employeeList.code, " +
            "employeeList.fullname, " +
            "employeeList.department, " +
            "employeeList.jobPosition, " +
            "employeeList.duty, " +
            "employeeList.dateJoin, " +
            "employeeList.dateOfBirth, " +
            "employeeList.gender," +
            "employeeList.accountStatus," +
            "employeeList.accountId," +
            "employeeList.contractId " +
            "FROM ( " +
            "SELECT ROW_NUMBER() OVER (ORDER by department.departmentLevel asc, department.name desc) AS RowNumber, " +
            "employee.id, " +
            "employee.employeeCode as code, " +
            "employee.fullname, " +
            "department.name as department, " +
            "jobPosition.name as jobPosition, " +
            "duty.name as duty, " +
            "employee.createdAt as dateJoin, " +
            "employee.dateOfBirth," +
            "account.status as accountStatus," +
            "account.id as accountId,  " +
            "CASE  " +
            "    WHEN employee.gender THEN 'Nam' " +
            "    ELSE 'Nữ' " +
            "END AS gender," +
            "contractGeneral.contractOriginal as contractId " +
            "from employee " +
            "left join account on employee.id = account.employeeId and account.isEnabled = true " +
            "left join  contractGeneral on employee.id =  contractGeneral.employeeId " +
            "left join department on  contractGeneral.departmentId = department.id  " +
            "left join jobPosition on  contractGeneral.jobPositionId = jobPosition.id " +
            "left join duty on jobPosition.dutyId = duty.id " +
            "where employee.isEnabled = true ");
            sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE));
            int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
            sql.append(" ) AS employeeList" +
    "  WHERE employeeList.RowNumber BETWEEN ").append((index-1) * 12 + 1).append(" AND ").append((index)*12);
            return query(sql.toString(), new EmployeeResponseMapper());
        }

        @Override
        public List<EmployeeTypeCount> getCountEmployeeFilter(EmployeeFilter filter) {
            StringBuilder sql = new StringBuilder("select " +
                "employeeType.id as type," +
                "COALESCE(countList.count, 0) as count " +
                "from employeeType left join  " +
                "( select employeeType.id as type, " +
                "count(employee.id) as count " +
                "from employeeType " +
                "left join employee on employeeType.id = employee.type " +
                "left join contractGeneral on employee.id = contractGeneral.employeeId " +
                "left join department on contractGeneral.departmentId = department.id " +
                "left join jobPosition on contractGeneral.jobPositionId = jobPosition.id " +
                "left join duty on jobPosition.dutyId = duty.id " +
                "where employee.isEnabled = true ");

            sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE));
            sql.append(" group by employeeType.id ) as countList on employeeType.id = countList.type ");
            return query(sql.toString(), new EmployeeTypeCountMapper());
        }

        @Override
        public ResumeProfileResponse getResumeProfile(Long id) {
            String sql = "call proc_GetResumeProfile(?)";
            List<ResumeProfileResponse> responses = query(sql, new ResumeProfileMapper(), id);
            return responses.get(0);
        }

        @Override
        public List<EmployeeNameAndCode> getListEmployee() {
            String sql = "select employee.id, employee.fullName as name, employee.employeeCode as code " +
            "from employee " +
            "where employee.isEnabled = true and  (employee.status != 3 OR employee.status IS NULL)";
            return query(sql, new EmployeeSelectMapper());
        }

        @Override
        public List<EmployeeTimeSheet> getTimeSheetEmployee(long employeeId, LocalDate dateStart, LocalDate dateEnd) {
            String sql = " select  " +
            "timekeeping.date as dateWorking, " +
            "timekeeping.workday, " +
            "timekeeping.timeIn, " +
            "timekeeping.timeOut, " +
            "timekeeping.timeLate, " +
            "timekeeping.timeEarly, " +
            "timekeeping.isLate, " +
            "letterInOut.id as letterId, " +
            "letterInOut.state as letterState " +
            " from timekeeping " +
            "left join timekeepinghasletter on timekeeping.id = timekeepinghasletter.timeKeepingId  " +
            "left join (select letter.id, letter.state  " +
            "from letter " +
            "inner join letterreason on letter.reasonId = letterreason.id and letterreason.letterTypeId = 4 " +
            ") as letterInOut on timekeepinghasletter.letterId  = letterInOut.id" +
            " where timekeeping.date between '" + dateStart +"' and '" + dateEnd + "' and timekeeping.employeeId = ? " +
            " order by timekeeping.date ";
            return query(sql, new EmployeeTimeSheetMapper(), employeeId);
        }

        @Override
        public List<LeaveLetterResponse> getListLeaveLetter(long employeeId, YearMonth yearMonth) {
            String sql = " select  " +
            "letter.id as letterId, " +
            "employee.id as employeeId, " +
            "letter.state as letterState, " +
            "letterreason.reason as letterReason, " +
            "letterreason.letterTypeId as letterType, " +
            "leaveletter.dateStart, " +
            "leaveletter.dateEnd, " +
            "leaveletter.createdAt as dateRegis," +
            "letterreason.workDayEnabled," +
            "leaveletter.total," +
            "letter.description " +
            "from letter " +
            "inner join letterreason on letter.reasonId = letterreason.id  " +
            "inner join leaveletter on letter.id = leaveletter.letterId  " +
            "inner join employee on letter.employeeId = employee.id  " +
            "where letter.isEnabled = 1 and employee.id = ? and Year(leaveletter.createdAt) = " + yearMonth.getYear() +
            " order by leaveletter.createdAt desc";
            return query(sql, new LeaveLetterResponseMapper(), employeeId);
    }

    @Override
    public List<OverTimeLetterResponse> getListOverTimeLetter(long employeeId, YearMonth yearMonth) {
        String sql = " select  " +
        "letter.id as letterId, " +
        "employee.id as employeeId, " +
        "letter.state as letterState, " +
        "letterreason.reason as letterReason, " +
        "letterreason.letterTypeId as letterType, " +
        "overtimeletter.timeStart, " +
        "overtimeletter.timeEnd, " +
        "overtimeletter.dateRegis, " +
        "overtimeletter.description " +
        "from letter " +
        "inner join letterreason on letter.reasonId = letterreason.id  " +
        "inner join overtimeletter on letter.id = overtimeletter.letterId  " +
        "inner join employee on letter.employeeId = employee.id  " +
        "where letter.isEnabled = 1 and employee.id = ? and Year(overtimeletter.dateRegis) = " + yearMonth.getYear() +
        " order by overtimeletter.dateRegis desc";
        return query(sql, new OverTimeLetterResponseMapper(), employeeId);
    }

    @Override
    public List<SalaryMonth> getListSalaryEmployee(long employeeId, String year) {
        StringBuilder sql = new StringBuilder("select " +
        "salaryTableDetail.id as salaryDetailId, " +
        "salaryTable.yearMonth,  " +
        "salaryTableDetail.salaryReal, " +
        "salaryTableDetail.totalTax " +
        "FROM salaryTable " +
        "inner join salaryTableDetail on salaryTableDetail.salaryTableId = salaryTable.id and salaryTable.yearMonth like '%").append(year).append("%' ")
        .append("where salaryTableDetail.employeeId = ").append(employeeId);
        return query(sql.toString(), new SalaryMonthEmployeeMapper());
    }

    @Override
    public SalaryDetailResponse getSalaryDetailEmployee(long salaryDetailId) {
        String sql = "select " +
                    "employee.id as employeeId,  " +
                    "employeeCode,  " +
                    "employee.fullName as employeeName,  " +
                    "null as department,  " +
                    "null as jobPosition, " +
                    "salaryTableDetail.workDayReal,  " +
                    "salaryTableDetail.totalWorkDay, " +
                    "salaryTableDetail.salaryGross, " +
                    "salaryTableDetail.salaryWorkDay,  " +
                    "salaryTableDetail.totalAllowance, " +
                    "salaryTableDetail.reward,  " +
                    "salaryTableDetail.penalty,  " +
                    "salaryTableDetail.salaryOTOnWeek,  " +
                    "salaryTableDetail.salaryOTLastWeek,  " +
                    "salaryTableDetail.salaryOTHoliday,  " +
                    "salaryTableDetail.totalInsurance,  " +
                    "salaryTableDetail.totalTax,  " +
                    "salaryTableDetail.salaryReal  " +
                    "from salaryTableDetail   " +
                    "inner join employee on salaryTableDetail.employeeId = employee.id  " +
                    "where salaryTableDetail.id = ?";
        List<SalaryDetailResponse> list = query(sql, new SalaryDetailResponseMapper(), salaryDetailId);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public SalaryAllowanceEmployee getSalaryAllowanceEmployee(long employeeId) {
        String sql = " select  " +
                " contractgeneral.employeeId , " +
                " contractgeneral.salaryGross, " +
                " allowance.name, " +
                " contractgeneralhasallowance.amount, " +
                " contractgeneralhasallowance.unit " +
                " from contractgeneral  " +
                " left join contractgeneralhasallowance on contractgeneral.id = contractgeneralhasallowance.contractId and contractgeneralhasallowance.isEnabled = 1 " +
                " left join allowance on contractgeneralhasallowance.allowanceId = allowance.id  " +
                " where contractgeneral.employeeId = ? ";
        List<SalaryAllowanceEmployee> list = queryDetails(sql, new SalaryAllowanceEmployeeMapper(), employeeId);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public EmployeeResponse getJobPositionByEmployeeId(long employeeId) {
        String sql = "select employee.id," +
                "employee.employeeCode as code," +
                "employee.fullName, " +
                "department.name as department, " +
                "jobPosition.name as jobPosition, " +
                "duty.name as duty " +
                "from employee " +
                "inner join contractGeneral on employee.id = contractGeneral.employeeId " +
                "inner join department on contractGeneral.departmentId = department.id " +
                "inner join jobPosition on contractGeneral.jobPositionId = jobPosition.id " +
                "inner join duty on jobPosition.dutyId = duty.id " +
                "where employee.id = ?";
        return query(sql, new EmployeeBasicResponseMapper(), employeeId).get(0);
    }
}
