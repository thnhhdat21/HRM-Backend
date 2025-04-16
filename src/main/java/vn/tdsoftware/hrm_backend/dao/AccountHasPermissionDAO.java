package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountHasPermissionDAO {
    void deleteAccountHasPermission(long accountId);
}
