package vn.tdsoftware.hrm_backend.dao.impl;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.ContractDAO;
import vn.tdsoftware.hrm_backend.dto.contract.response.*;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.mapper.response.CountContractTypeMapper;
import vn.tdsoftware.hrm_backend.mapper.response.contract.*;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.time.LocalDate;
import java.util.List;

@Component
public class ContractDAOImpl extends AbstractDao<Work> implements ContractDAO {

    @Override
    public WorkProfileResponse getWorkProfile(long employeeId) {
        String sql = "select  " +
                "    employee.id as employeeId," +
                "    employee.fullName as employeeName," +
                "    employee.employeeCode as employeeCode,  " +
                "    employee.status,  " +
                "    employee.type,  " +
                "    department.name department,  " +
                "    duty.name as duty,  " +
                "    jobPosition.name as jobPosition,  " +
                "    employee.createdAt as dateJoin,  " +
                "    contractGeneral.dateSign, " +
                "    contractGeneral.dateOnBoard, " +
                "    contractType.name as contractName,  " +
                "    contractGeneral.salaryGross,   " +
                "    account.username as account,  " +
                "    role.name as role  " +
                "    from employee " +
                "    inner join contractGeneral on employee.id = contractGeneral.employeeId " +
                "    inner join contractType on contractGeneral.type = contractType.id " +
                "    inner join account on employee.id = account.employeeId  " +
                "    inner join department on contractGeneral.departmentId = department.id   " +
                "    inner join jobPosition on contractGeneral.jobPositionId = jobPosition.id  " +
                "    inner join duty on jobPosition.dutyId = duty.id  " +
                "    inner join role on jobPosition.roleId = role.id  " +
                "    where employee.isEnabled = true  " +
                "    and employee.id = ?";
        List<WorkProfileResponse> response = query(sql, new WorkProfileResponseMapper(), employeeId);
        return response.isEmpty() ? null : response.get(0);
    }

    @Override
    public List<WorkProcessResponse> getWorkProcess(long employeeId) {
        String sql = "select " +
                "contract.dateStart, " +
                "contract.dateSign, " +
                "contract.status, " +
                "department.name as department, " +
                "jobPosition.name as jobPosition, " +
                "duty.name as duty, " +
                "contractType.name as contractType, " +
                "contract.code " +
                "from contract " +
                "inner join contractType on contract.type = contractType.id " +
                "inner join department on contract.departmentId = department.id " +
                "inner join jobPosition on contract.jobPositionId = jobPosition.id " +
                "inner join duty on jobPosition.dutyId = duty.id " +
                "where contract.isEnabled = true and contract.parent is null " +
                "and contract.employeeId = ? " +
                "order by contract.dateStart desc";
        return query(sql, new WorkProcessMapper(), employeeId);
    }

    @Override
    public ContractProfileResponse getContractProfileByEmployee(long employeeId) {
        String sql = "select " +
                "contract.id as contractId, "+
                "contract.code as contractCode, " +
                "contract.state as contractState, " +
                "contract.parent, " +
                "employee.fullName as employeeName, " +
                "employee.employeeCode as employeeCode, " +
                "contractType.name as contractType, " +
                "department.name as department, " +
                "jobPosition.name as jobPosition, " +
                "duty.name as duty, " +
                "contract.method, " +
                "contract.dateStart, " +
                "contract.dateSign," +
                "contract.dateEnd,  " +
                "contract.status as contractStatus, " +
                "contract.salaryGross," +
                "contract.description, " +
                "allowance.name as allowanceName, " +
                "contractHasAllowance.amount as allowanceAmount, " +
                "contractHasAllowance.unit as allowanceUnit " +
                "from contract " +
                "left join employee on contract.employeeId = employee.id and contract.isActive = true " +
                "left join contractType on contract.type = contractType.id " +
                "left join contractHasAllowance on contract.id = contractHasAllowance.contractId and contractHasAllowance.isEnabled = true " +
                "left join allowance on contractHasAllowance.allowanceId = allowance.id  and allowance.isEnabled = true " +
                "left join department on contract.departmentId = department.id " +
                "left join jobPosition on contract.jobPositionId = jobPosition.id " +
                "left join duty on jobPosition.dutyId = duty.id " +
                "where employee.isEnabled = true " +
                "and employee.id = ? ";
        List<ContractProfileResponse> response = queryDetails(sql, new ContractProfileMapper(), employeeId);
        return response.get(0);
    }

    @Override
    public ContractProfileResponse getContractProfileByContractId(long contractId) {
        String sql = " select " +
                "contract.id as contractId, "+
                "contract.code as contractCode, " +
                "contract.state as contractState, " +
                "contract.parent, " +
                "employee.fullName as employeeName, " +
                "employee.employeeCode as employeeCode, " +
                "contractType.name as contractType, " +
                "department.name as department, " +
                "jobPosition.name as jobPosition, " +
                "duty.name as duty, " +
                "contract.method, " +
                "contract.dateStart, " +
                "contract.dateSign," +
                "contract.dateEnd,  " +
                "contract.status as contractStatus, " +
                "contract.salaryGross, " +
                "contract.description, " +
                "allowance.name as allowanceName, " +
                "contractHasAllowance.amount as allowanceAmount, " +
                "contractHasAllowance.unit as allowanceUnit " +
                "from contract " +
                "left join employee on contract.employeeId = employee.id " +
                "left join contractType on contract.type = contractType.id " +
                "left join contractHasAllowance on contract.id = contractHasAllowance.contractId and contractHasAllowance.isEnabled = true " +
                "left join allowance on contractHasAllowance.allowanceId = allowance.id  and allowance.isEnabled = true " +
                "left join department on contract.departmentId = department.id " +
                "left join jobPosition on contract.jobPositionId = jobPosition.id " +
                "left join duty on jobPosition.dutyId = duty.id " +
                "where contract.isEnabled = true " +
                "and contract.id = ? ";
        List<ContractProfileResponse> response = queryDetails(sql, new ContractProfileMapper(), contractId);
        return response.get(0);
    }

    @Override
    public ContractDetailResponse getContractDetail(long contractId) {
        String sql = "select   " +
                "contract.id as contractId, " +
                "contract.code as contractCode," +
                "contract.parent," +
                "employee.id as employeeId,  " +
                "employee.fullName as employeeName, " +
                "employee.employeeCode as employeeCode, " +
                "contract.type as contractType,   " +
                "contract.departmentId as department, " +
                "contract.jobPositionId as jobPosition,   " +
                "jobPosition.dutyId as duty,   " +
                "contract.method,   " +
                "contract.dateStart,   " +
                "contract.dateSign," +
                "contract.dateEnd,   " +
                "contract.salaryGross,  " +
                "contractHasAllowance.id as contractHasAllowanceId,  " +
                "allowance.id as allowanceId,  " +
                "contractHasAllowance.amount as allowanceAmount,  " +
                "contractHasAllowance.unit as allowanceUnit  " +
                "from contract   " +
                "left join employee on contract.employeeId = employee.id  " +
                "left join contractHasAllowance on contract.id = contractHasAllowance.contractId and contractHasAllowance.isEnabled = true  " +
                "left join allowance on contractHasAllowance.allowanceId = allowance.id and allowance.isEnabled = true " +
                "left join department on contract.departmentId = department.id   " +
                "left join jobPosition on contract.jobPositionId = jobPosition.id   " +
                "where contract.id = ?";
        List<ContractDetailResponse> response = queryDetails(sql, new ContractDetailMapper(), contractId);
        return response.get(0);
    }

    @Override
    public List<ContractOfEmployeeResponse> getListContractOfEmployee(long employeeId) {
        String sql = "select " +
                "contract.id, " +
                "contract.code, " +
                "contractType.name, " +
                "contract.status, " +
                "contract.state, " +
                "contract.dateSign, " +
                "contract.dateStart " +
                "from contract " +
                "inner join contractType on contract.type = contractType.id " +
                "where contract.isEnabled = true " +
                "and contract.employeeId = ? " +
                "order by contract.dateStart desc";
        return query(sql, new ContractOfEmployeeResponseMapper(), employeeId);
    }

    @Override
    public List<ContractResponse> getListContract(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder(
                        "SELECT " +
                        "contractList.createdBy, " +
                        "contractList.contractCode, " +
                        "contractList.contractId, " +
                        "contractList.employeeId, " +
                        "contractList.employeeCode, " +
                        "contractList.employeeName, " +
                        "contractList.department, " +
                        "contractList.contractName, " +
                        "contractList.contractStatus, " +
                        "contractList.dateSign, " +
                        "contractList.dateEnd," +
                        "contractList.contractState," +
                        "contractList.contractDateLiquid " +
                        "FROM ( " +
                        "SELECT ROW_NUMBER() OVER () AS RowNumber, " +
                        "eC.fullName as createdBy, " +
                        "contract.code as contractCode, " +
                        "contract.id  as contractId, " +
                        "employee.id as employeeId," +
                        "employee.employeeCode, " +
                        "employee.fullName as employeeName, " +
                        "department.name as department, " +
                        "contractType.name as contractName, " +
                        "contract.status as contractStatus, " +
                        "contract.dateSign, " +
                        "contract.dateEnd," +
                        "contract.state as contractState," +
                        "contract.dateLiquidation as contractDateLiquid " +
                        "from contract " +
                        "left join employee ec on contract.createdBy = ec.id " +
                        "left join employee on contract.employeeId = employee.id " +
                        "left join department on contract.departmentId = department.id " +
                        "left join contractType on contract.type = contractType.id " +
                        "where contract.isEnabled = true " +
                        "and contract.parent is null  ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_CONTRACT));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) AS contractList " +
                "  WHERE contractList.RowNumber BETWEEN ").append((index-1) * 12 + 1).append(" AND ").append((index)*12);
        return query(sql.toString(), new ContractResponseMapper());
    }

    @Override
    public int countContractAppendix(long contractId) {
        String sql = "select count(contract.id) " +
                "from contract " +
                "where contract.isEnabled = true " +
                "and contract.parent = ? ";
        return count(sql, contractId);
    }

    @Override
    public List<CountContractTypeResponse> getCountContractType(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("select " +
                "contractType.id, " +
                "contractType.name, " +
                "COALESCE(countList.count, 0) as count " +
                "from contractType left join " +
                "(select contractType.id , contractType.name, count(contract.id) as count " +
                "from contract " +
                "left join employee ec on contract.createdBy = ec.id " +
                "left join employee on contract.employeeId = employee.id " +
                "left join department on contract.departmentId = department.id " +
                "left join contractType on contract.type = contractType.id " +
                "where contract.isEnabled = true and contract.parent is null ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_CONTRACT));
        sql.append(" group by contractType.id , contractType.name) countList on contractType.id = countList.id");
        return query(sql.toString(), new CountContractTypeMapper());
    }

    @Override
    public int getCountContract(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder(
                        "SELECT count(contract.id)" +
                        "from contract " +
                        "left join employee ec on contract.createdBy = ec.id " +
                        "left join employee on contract.employeeId = employee.id " +
                        "left join department on contract.departmentId = department.id " +
                        "left join contractType on contract.type = contractType.id " +
                        "where contract.isEnabled = true " +
                        "and contract.parent is null  ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_CONTRACT));
        return count(sql.toString());
    }

}
