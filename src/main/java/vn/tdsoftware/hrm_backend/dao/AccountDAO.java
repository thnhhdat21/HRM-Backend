package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountCountResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountDetailResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;

import java.util.List;

@Repository
public interface AccountDAO {
    List<AccountResponse> getListAccount(int type);
    AccountCountResponse getAccountCount();
    void deleteAccount(long id);
    AccountDetailResponse getAccountDetail(long id);
}
