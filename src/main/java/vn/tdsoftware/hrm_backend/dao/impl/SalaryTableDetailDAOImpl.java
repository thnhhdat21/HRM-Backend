package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.SalaryTableDetailDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.TaxDTO;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.TaxResponse;
import vn.tdsoftware.hrm_backend.entity.SalaryTableDetail;
import vn.tdsoftware.hrm_backend.mapper.response.salary.SalaryDetailResponseMapper;
import vn.tdsoftware.hrm_backend.mapper.response.salary.TaxDTOMapper;
import vn.tdsoftware.hrm_backend.mapper.response.timekeeping.EmployeeTimeKeepingMapper;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.util.List;

@Component
public class SalaryTableDetailDAOImpl extends AbstractDao<SalaryTableDetail> implements SalaryTableDetailDAO {

    @Override
    public List<SalaryDetailResponse> getListSalaryDetail(EmployeeFilter filter) {
        StringBuilder sql =  new StringBuilder("SELECT  " +
                "salaryTable.employeeId, " +
                "salaryTable.employeeCode, " +
                "salaryTable.employeeName, " +
                "salaryTable.department, " +
                "salaryTable.jobPosition, " +
                "salaryTable.workDayReal, " +
                "salaryTable.totalWorkDay, " +
                "salaryTable.salaryGross, " +
                "salaryTable.salaryWorkDay, " +
                "salaryTable.totalAllowance, " +
                "salaryTable.reward, " +
                "salaryTable.penalty, " +
                "salaryTable.salaryOTOnWeek, " +
                "salaryTable.salaryOTLastWeek, " +
                "salaryTable.salaryOTHoliday, " +
                "salaryTable.totalInsurance, " +
                "salaryTable.totalTax, " +
                "salaryTable.salaryReal  " +
                "FROM ( " +
                "SELECT ROW_NUMBER() OVER (ORDER BY department.departmentLevel ASC, department.name DESC, employee.fullname ASC) AS RowNumber, " +
                "employee.id as employeeId, " +
                "employeeCode, " +
                "employee.fullName as employeeName, " +
                "department.name as department, " +
                "jobPosition.name as jobPosition," +
                "salaryTableDetail.workDayReal, " +
                "salaryTableDetail.totalWorkDay, " +
                "salaryTableDetail.salaryGross, " +
                "salaryTableDetail.salaryWorkDay, " +
                "salaryTableDetail.totalAllowance, " +
                "salaryTableDetail.reward, " +
                "salaryTableDetail.penalty, " +
                "salaryTableDetail.salaryOTOnWeek, " +
                "salaryTableDetail.salaryOTLastWeek, " +
                "salaryTableDetail.salaryOTHoliday, " +
                "salaryTableDetail.totalInsurance, " +
                "salaryTableDetail.totalTax, " +
                "salaryTableDetail.salaryReal  " +
                "from salaryTableDetail  " +
                "inner join employee on salaryTableDetail.employeeId = employee.id " +
                "inner join department on salaryTableDetail.department = department.id " +
                "inner join jobPosition on salaryTableDetail.jobPosition = jobPosition.id " +
                "where salaryTableDetail.salaryTableId = " + filter.getSalaryTableId());
                sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) AS salaryTable " +
                "  WHERE salaryTable.RowNumber BETWEEN ").append((index -1)*12 + 1).append(" AND ").append((index -1)*12 + 12);
        return query(sql.toString(), new SalaryDetailResponseMapper());
    }

    @Override
    public int getCountSalaryDetail(EmployeeFilter filter) {
        StringBuilder sql =  new StringBuilder("select count(*) " +
                "from salaryTableDetail  " +
                "inner join employee on salaryTableDetail.employeeId = employee.id " +
                "inner join department on salaryTableDetail.department = department.id " +
                "inner join jobPosition on salaryTableDetail.jobPosition = jobPosition.id " +
                "where salaryTableDetail.salaryTableId = " + filter.getSalaryTableId());
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE));
        return count(sql.toString());
    }

    @Override
    public List<TaxDTO> getListTax(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder(" WITH employeeList AS ( " +
                "SELECT  " +
                "ROW_NUMBER() OVER (ORDER BY department.departmentLevel ASC, department.name DESC, employee.fullName ASC) AS RowNumber,  " +
                "employee.id, employee.employeeCode as code, employee.fullName, " +
                "department.name as department, " +
                "jobPosition.name as jobPosition " +
                "FROM employee " +
                "INNER JOIN contractGeneral ON employee.id = contractGeneral.employeeId " +
                "INNER JOIN department ON contractGeneral.departmentId = department.id " +
                "INNER JOIN jobPosition ON contractGeneral.jobPositionId = jobPosition.id " +
                "WHERE employee.isEnabled = true and employee.status = 1 ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) " +
                        "SELECT " +
                        "employeeList.id as employeeId, " +
                        "employeeList.code as employeeCode, " +
                        "employeeList.fullName as employeeName, " +
                        "employeeList.department, " +
                        "employeeList.jobPosition, " +
                        "salaryTable.yearMonth, " +
                        "salaryTableDetail.salaryReal, " +
                        "salaryTableDetail.totalTax " +
                        "FROM salaryTable " +
                        "inner join salaryTableDetail on salaryTableDetail.salaryTableId = salaryTable.id and salaryTable.yearMonth like '%").append(filter.getYear()).append("%' " +
                        "right join employeeList on salaryTableDetail.employeeId = employeeList.id ")
                        .append("WHERE employeeList.RowNumber BETWEEN ").append((index-1) * 12 + 1).append(" AND ").append((index)*12)
                .append(" ORDER BY employeeList.RowNumber ");
        return query(sql.toString(), new TaxDTOMapper());
    }

    @Override
    public int getCountTax(EmployeeFilter filter) {
        return 0;
    }
}
