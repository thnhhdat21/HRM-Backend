package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleDetailResponse;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleResponse;

import java.util.List;

@Repository
public interface RoleDAO {
    List<RoleResponse> getListRole();
    RoleDetailResponse getRoleDetail(int id);
    void removeRoleHasPermission(int roleId);
    void removeRole(int id);
    String getPermission(int id);
    List<String> getRoleByUsername(String username);
}
