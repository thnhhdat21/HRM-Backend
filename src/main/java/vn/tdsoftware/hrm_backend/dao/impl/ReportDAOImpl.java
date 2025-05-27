package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.ReportDAO;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportCountEmployee;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryDepartment;
import vn.tdsoftware.hrm_backend.mapper.response.report.ReportCountEmployeeMapper;
import vn.tdsoftware.hrm_backend.mapper.response.report.ReportSalaryDepartmentMapper;

import java.time.YearMonth;
import java.util.List;

@Component
public class ReportDAOImpl extends AbstractDao<Object> implements ReportDAO {

    @Override
    public List<ReportCountEmployee> reportCountEmployee() {
        String sql = "select department.name , count(employee.id) as countEmployee " +
                "from employee  " +
                "inner join contractgeneral on employee.id = contractgeneral.employeeId  " +
                "inner join department on contractgeneral.departmentId = department.id  " +
                "where employee.status != 3 and employee.isEnabled = 1 " +
                "group by department.name";
        return query(sql, new ReportCountEmployeeMapper());
    }

    @Override
    public List<ReportSalaryDepartment> reportSalaryDepartment(YearMonth yearMonth) {
        System.out.println(yearMonth.getMonth());
        String sql = "select department.name, sum(salaryTableDetail.salaryReal) as sumSalary " +
                "from salarytabledetail inner join department on salarytabledetail.department = department.id " +
                "inner join salaryTable on salarytabledetail.salaryTableId  = salaryTable.id  " +
                "where CAST(SUBSTRING(salaryTable.yearMonth, 6, 2) AS UNSIGNED) = ? and CAST(SUBSTRING(salaryTable.yearMonth, 1, 4) AS UNSIGNED) = ? " +
                "group by department.name";
        return query(sql, new ReportSalaryDepartmentMapper(), yearMonth.getMonth().getValue(), yearMonth.getYear());
    }
}
