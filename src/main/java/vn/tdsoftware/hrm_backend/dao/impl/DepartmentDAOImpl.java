package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.DepartmentDAO;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentTreeResponse;
import vn.tdsoftware.hrm_backend.entity.Department;
import vn.tdsoftware.hrm_backend.mapper.response.department.DepartmentTreeResponseMapper;

import java.util.List;

@Component
public class DepartmentDAOImpl extends AbstractDao<Department> implements DepartmentDAO {

    @Override
    public List<DepartmentTreeResponse> findAll() {
        String sqlQuery = "call proc_GetListDepartment()";
        List<DepartmentTreeResponse> listResponse = query(sqlQuery, new DepartmentTreeResponseMapper());
        return listResponse;
    }

    @Override
    public int countEmployeeInDepartment(Long id) {
        String sqlQuery = " select count(employee.id) as countEmployee " +
                "from department " +
                "inner join employee on department.id = employee.departmentId " +
                "where department.id = ?";
        return count(sqlQuery, id);
    }

    @Override
    public void deleteDepartment(Long id) {
        String sqlQuery = "update Department " +
                "set isEnabled = 0 " +
                "where id  = ? ";
        update(sqlQuery, id);
    }
}
