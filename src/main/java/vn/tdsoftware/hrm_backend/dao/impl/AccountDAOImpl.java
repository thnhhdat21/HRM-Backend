package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.AccountDAO;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountCountResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountDetailResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;
import vn.tdsoftware.hrm_backend.entity.Account;
import vn.tdsoftware.hrm_backend.mapper.response.account.AccountCountResponseMapper;
import vn.tdsoftware.hrm_backend.mapper.response.account.AccountDetailMapper;
import vn.tdsoftware.hrm_backend.mapper.response.account.AccountResponseMapper;
import vn.tdsoftware.hrm_backend.util.constant.AccountConstant;

import java.util.List;

@Component
public class AccountDAOImpl extends AbstractDao<Account> implements AccountDAO {

    @Override
    public List<AccountResponse> getListAccount(int type) {
        String sqlQuery = "Call proc_GetListAccount(?)";
        List<AccountResponse> list = query(sqlQuery, new AccountResponseMapper(), type);
        return list;
    }

    @Override
    public AccountCountResponse getAccountCount() {
        String sql = "SELECT " +
                "SUM(CASE WHEN account.status = 1 THEN 1 ELSE 0 END) AS countActive, " +
                "SUM(CASE WHEN account.status = 2 THEN 1 ELSE 0 END) AS countNotActive, " +
                "SUM(CASE WHEN account.status = 3 THEN 1 ELSE 0 END) AS countLock " +
                "FROM account " +
                "where account.isEnabled = true; ";
        List<AccountCountResponse> list = query(sql, new AccountCountResponseMapper());
        return list.get(0);
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
