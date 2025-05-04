package vn.tdsoftware.hrm_backend.dao.impl;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.ContractDAO;
import vn.tdsoftware.hrm_backend.dto.work.response.ContractResponse;
import vn.tdsoftware.hrm_backend.mapper.response.work.ContractResponseMapper;

import java.util.List;

@Component
public class ContractDAOImpl extends AbstractDao<Work> implements ContractDAO {

    @Override
    public ContractResponse getWorkProfile(long employeeId) {
        String sql = "select " +
                "employee.id as employeeId, " +
                "employee.status, " +
                "employee.type, " +
                "department.name department, " +
                "duty.name as duty, " +
                "jobPosition.name as jobPosition, " +
                "employee.createdAt as dateJoin, " +
                "contract.dateSign, " +
                "contractType.name as contractName, " +
                "contract.salaryGross,  " +
                "account.username as account, " +
                "role.name as role " +
                "from employee " +
                "inner join account on employee.id = account.employeeId " +
                "inner join contract on employee.id = contract.employeeId " +
                "inner join contractType on contract.type = contractType.id " +
                "inner join department on contract.departmentId = department.id  " +
                "inner join jobPosition on contract.jobPositionId = jobPosition.id " +
                "inner join duty on jobPosition.dutyId = duty.id " +
                "inner join role on jobPosition.roleId = role.id " +
                "where employee.isEnabled = true " +
                "and contract.isEnabled = true " +
                "and contract.status = 1 " +
                "and employee.id = ? ";
        List<ContractResponse> response = query(sql, new ContractResponseMapper(), employeeId);
        return response.get(0);
    }
}
