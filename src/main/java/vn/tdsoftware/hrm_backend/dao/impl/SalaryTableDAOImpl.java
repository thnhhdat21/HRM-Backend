package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.SalaryTableDAO;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryTableResponse;
import vn.tdsoftware.hrm_backend.entity.SalaryTable;
import vn.tdsoftware.hrm_backend.mapper.response.salary.SalaryTableResponseMapper;

import java.util.List;

@Component
public class SalaryTableDAOImpl extends AbstractDao<SalaryTable> implements SalaryTableDAO {
    @Override
    public List<SalaryTableResponse> getListSalaryTableOfDepartment(long departmentId) {
        String sql = "select  " +
                "salaryTable.yearMonth, " +
                "salaryTable.id, " +
                "salaryTable.name ,  " +
                "salaryTable.status, " +
                "salaryTable.createdAt, " +
                "count(distinct salarytabledetail.employeeId) as numberEmployee, " +
                "sum(salarytabledetail.salaryReal) as totalAmount " +
                "from salarytable inner join salarytabledetail on salarytable.id = salarytabledetail.salaryTableId  " +
                "inner join employee on salarytabledetail.employeeId = employee.id  " +
                "inner join contractgeneral on employee.id = contractgeneral.employeeId " +
                "where contractgeneral.departmentId = ? " +
                "group by salaryTable.yearMonth, " +
                "salaryTable.id, " +
                "salaryTable.name ,  " +
                "salaryTable.status, " +
                "salaryTable.createdAt ";
        return query(sql, new SalaryTableResponseMapper(), departmentId);
    }
}
