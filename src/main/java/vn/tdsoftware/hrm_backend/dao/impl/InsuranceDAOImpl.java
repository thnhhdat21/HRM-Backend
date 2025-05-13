package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.InsuranceDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.insurance.response.InsuranceResponse;
import vn.tdsoftware.hrm_backend.entity.Insurance;
import vn.tdsoftware.hrm_backend.mapper.response.employee.EmployeeResponseMapper;
import vn.tdsoftware.hrm_backend.mapper.response.insurance.InsuranceResponseMapper;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.util.List;

@Component
public class InsuranceDAOImpl extends AbstractDao<Insurance> implements InsuranceDAO {

    @Override
    public List<InsuranceResponse> getListInsurance(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT " +
                "employeeList.id," +
                "employeeList.code, " +
                "employeeList.fullname, " +
                "employeeList.department, " +
                "employeeList.jobPosition, " +
                "employeeList.duty, " +
                "employeeList.insuranceNumber," +
                "employeeList.insuranceCard " +
                "FROM ( " +
                "SELECT ROW_NUMBER() OVER (ORDER by department.departmentLevel asc, department.name desc) AS RowNumber, " +
                "employee.id, " +
                "employee.employeeCode as code, " +
                "employee.fullname, " +
                "department.name as department, " +
                "jobPosition.name as jobPosition, " +
                "duty.name as duty, " +
                "employee.insuranceNumber, " +
                "employee.insuranceCard  " +
                "from employee " +
                "left join  contractGeneral on employee.id =  contractGeneral.employeeId " +
                "left join department on  contractGeneral.departmentId = department.id  " +
                "left join jobPosition on  contractGeneral.jobPositionId = jobPosition.id " +
                "left join duty on jobPosition.dutyId = duty.id " +
                "inner join insurance on employee.id = insurance.employeeId " +
                "where employee.isEnabled = true and insurance.yearMonth = '" + filter.getYearMonth() + "' ");
        if (filter.getStatus() == null || filter.getStatus() == 0){
            sql.append(" and insurance.status != 3 ");
        }
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_INSURANCE));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) AS employeeList" +
                "  WHERE employeeList.RowNumber BETWEEN ").append((index -1)*12 + 1).append(" AND ").append((index -1)*12 + 12);
        return query(sql.toString(), new InsuranceResponseMapper());
    }

    @Override
    public int getCountInsurance(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("select count(employee.id) " +
                "from employee " +
                "inner join insurance on employee.id = insurance.employeeId " +
                "where employee.isEnabled = true and insurance.yearMonth = '" + filter.getYearMonth() + "' ");
        if (filter.getStatus() == null || filter.getStatus() == 0){
            sql.append(" and insurance.status != 3 ");
        }
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_INSURANCE));
        return count(sql.toString());
    }
}
