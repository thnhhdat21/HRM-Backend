package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.AccountDAO;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountTypeCount;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountDetailResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.entity.Account;
import vn.tdsoftware.hrm_backend.mapper.response.account.AccountTypeCountMapper;
import vn.tdsoftware.hrm_backend.mapper.response.account.AccountDetailMapper;
import vn.tdsoftware.hrm_backend.mapper.response.account.AccountResponseMapper;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.util.List;

@Component
public class AccountDAOImpl extends AbstractDao<Account> implements AccountDAO {

    @Override
    public List<AccountResponse> getListAccount(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT " +
                "accountList.id," +
                "accountList.username, " +
                "accountList.fullname, " +
                "accountList.role, " +
                "accountList.employeeCode, " +
                "accountList.fullname, " +
                "accountList.department, " +
                "accountList.createdAt as createDate, " +
                "accountList.activeAt as activeDate " +
                "FROM ( " +
                "SELECT ROW_NUMBER() OVER (ORDER by department.departmentLevel asc, department.name desc) AS RowNumber, " +
                "account.id, " +
                "account.username, " +
                "role.name as role, " +
                "employee.employeeCode,  " +
                "employee.fullname, " +
                "department.name as department, " +
                "account.createdAt, " +
                "account.activeAt " +
                "from account  " +
                "inner join employee on account.employeeId = employee.id  " +
                "inner join contractgeneral on employee.id = contractGeneral.employeeId  " +
                "inner join department on contractGeneral.departmentId = department.id  " +
                "inner join jobposition on contractGeneral.jobPositionId = jobPosition.id  " +
                "inner join duty on jobPosition.dutyId = duty.id  " +
                "inner join role on jobPosition.roleId = role.id " +
                "where account.isEnabled = true ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_ACCOUNT));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) AS accountList" +
                "  WHERE accountList.RowNumber BETWEEN ").append((index-1) * 12 + 1).append(" AND ").append((index)*12);
        return query(sql.toString(), new AccountResponseMapper());
    }


    @Override
    public  List<AccountTypeCount> getAccountCountType(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("select account.status , count(account.id) as count " +
                "from account  " +
                "inner join employee on account.employeeId = employee.id and account.isEnabled = true " +
                "inner join contractgeneral on employee.id = contractGeneral.employeeId  " +
                "inner join department on contractGeneral.departmentId = department.id  " +
                "inner join jobposition on contractGeneral.jobPositionId = jobPosition.id  " +
                "inner join duty on jobPosition.dutyId = duty.id  " +
                "inner join role on jobPosition.roleId = role.id " +
                "where 1 = 1 ");
        sql.append(SQLUtil.sqlFilter(filter, 0));
        sql.append("group by account.status");
        List<AccountTypeCount> list = query(sql.toString(), new AccountTypeCountMapper());
        return list;
    }

    @Override
    public int getCountAccount(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("select count(account.id) " +
                "from account  " +
                "inner join employee on account.employeeId = employee.id and account.isEnabled = true " +
                "inner join contractgeneral on employee.id = contractGeneral.employeeId  " +
                "inner join department on contractGeneral.departmentId = department.id  " +
                "inner join jobposition on contractGeneral.jobPositionId = jobPosition.id  " +
                "inner join duty on jobPosition.dutyId = duty.id  " +
                "inner join role on jobPosition.roleId = role.id " +
                "where 1 = 1 ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_ACCOUNT));
        return count(sql.toString());
    }

    @Override
    public void deleteAccount(long id) {
        String sqlQuery = "update account " +
                "set isEnabled = 0 " +
                "where id  = ? ";
        update(sqlQuery, id);
    }

    @Override
    public AccountDetailResponse getAccountDetail(long id) {
        String sqlQueryWithRole = "call proc_GetAccountWithRole(?) ";
        List<AccountDetailResponse> responseList;
        responseList = query(sqlQueryWithRole, new AccountDetailMapper(), id);
        return responseList.get(0);
    }
}
