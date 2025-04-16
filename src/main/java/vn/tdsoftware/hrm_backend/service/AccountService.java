package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.account.request.ActiveAccountRequest;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountCountResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountDetailResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;

import java.util.List;

public interface AccountService {
    List<AccountResponse> getListAccount(int type);
    AccountCountResponse getAccountCount();
    void lockAccount(long id);
    void unlockAccount(long id);
    void changePassword(long id, String password) ;
    void updatePermission(long id, String permissions) ;
    String activeAccount(ActiveAccountRequest request);
    void deleteId(long id);
    AccountDetailResponse  getDetailAccount(long id);
}
