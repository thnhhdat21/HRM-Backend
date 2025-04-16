package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.AccountHasPermissionDAO;
import vn.tdsoftware.hrm_backend.entity.AccountHasPermission;

@Component
public class AccountHasPermissionDAOImpl extends AbstractDao<AccountHasPermission> implements AccountHasPermissionDAO {
    @Override
    public void deleteAccountHasPermission(long accountId) {
        String sqlQuery = "update AccountHasPermission " +
                "set isEnabled = 0 " +
                "where accountId  = ? ";
        update(sqlQuery, accountId);
    }
}
