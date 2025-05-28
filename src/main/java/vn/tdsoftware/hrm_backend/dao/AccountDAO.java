package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountTypeCount;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;

import java.util.List;

@Repository
public interface AccountDAO {
    List<AccountResponse> getListAccount(EmployeeFilter filter);
    List<AccountTypeCount> getAccountCountType(EmployeeFilter filter);
    int getCountAccount(EmployeeFilter filter);
    void deleteAccount(long id);
}
