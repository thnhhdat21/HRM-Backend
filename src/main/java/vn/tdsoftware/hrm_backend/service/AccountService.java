package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.account.request.ActiveAccountRequest;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountDetailResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;

import java.util.List;

public interface AccountService {
    List<AccountResponse> getListAccount(EmployeeFilter filter);
    AccountTypeResponse getAccountCountType(EmployeeFilter filter);
    int getAccountCount(EmployeeFilter filter);
    void lockAccount(long id);
    void unlockAccount(long id);
    void changePassword(long id, String password) ;
    String activeAccount(ActiveAccountRequest request);
    void deleteId(long id);
    AccountDetailResponse  getDetailAccount(long id);
    void createAccount(long employeeId, String employeeName);
}
