package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.OnLeaveDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.onleave.response.EmployeeOnLeaveResponse;
import vn.tdsoftware.hrm_backend.entity.OnLeave;
import vn.tdsoftware.hrm_backend.mapper.response.onleave.EmployeeOnLeaveMapper;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.util.List;

@Component
public class OnLeaveDAOImpl extends AbstractDao<OnLeave> implements OnLeaveDAO {
    @Override
    public List<EmployeeOnLeaveResponse> getEmployeeOnLeave(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT " +
                "employeeList.employeeId, " +
                "employeeList.employeeCode, " +
                "employeeList.employeeName,  " +
                "employeeList.department,  " +
                "employeeList.jobPosition,  " +
                "employeeList.seniorDay, " +
                "employeeList.regulaDay, " +
                "employeeList.usedDay " +
                "FROM ( " +
                "SELECT ROW_NUMBER() OVER (ORDER BY department.departmentLevel ASC, department.name DESC, employee.fullName ASC) AS RowNumber, " +
                "employee.id as employeeId, " +
                "employee.employeeCode, " +
                "employee.fullName as employeeName, " +
                "department.name as department, " +
                "jobposition.name as jobPosition, " +
                "onleave.seniorDay, " +
                "onleave.regulaDay, " +
                "onleave.usedDay " +
                "from onLeave " +
                "right join employee on onLeave.employeeId = employee.id and onLeave.year = "+ filter.getYear() +
                " inner join  contractGeneral on employee.id = contractGeneral.employeeId " +
                "inner join department on  contractGeneral.departmentId = department.id  " +
                "inner join jobPosition on  contractGeneral.jobPositionId = jobPosition.id " +
                "inner join duty on jobPosition.dutyId = duty.id " +
                "where employee.isEnabled = true and  contractGeneral.status = 1 ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) AS employeeList" +
                "  WHERE employeeList.RowNumber BETWEEN ").append((index -1)*12 + 1).append(" AND ").append((index -1)*12 + 12);
        return query(sql.toString(), new EmployeeOnLeaveMapper());
    }

    @Override
    public int getCountEmployee(EmployeeFilter filter) {
        String sql = "SELECT COUNT(DISTINCT employee.id) " +
                "FROM employee " +
                "INNER JOIN contractGeneral ON employee.id = contractGeneral.employeeId " +
                "INNER JOIN department ON contractGeneral.departmentId = department.id " +
                "INNER JOIN jobPosition ON contractGeneral.jobPositionId = jobPosition.id " +
                "WHERE employee.isEnabled = true AND contractGeneral.status = 1 " + SQLUtil.sqlFilter(filter, FilterConstant.TYPE_EMPLOYEE);
        return count(sql);
    }
}
