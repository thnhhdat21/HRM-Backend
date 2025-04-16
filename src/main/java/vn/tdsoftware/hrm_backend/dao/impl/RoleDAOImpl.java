package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.RoleDAO;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleDetailResponse;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleResponse;
import vn.tdsoftware.hrm_backend.entity.Role;
import vn.tdsoftware.hrm_backend.mapper.response.role.ListPermissionByRoleMapper;
import vn.tdsoftware.hrm_backend.mapper.response.role.RoleDetailResponseMapper;
import vn.tdsoftware.hrm_backend.mapper.response.role.RoleResponseMapper;

import java.util.List;

@Component
public class RoleDAOImpl extends AbstractDao<Role> implements RoleDAO {

    @Override
    public List<RoleResponse> getListRole() {
        String sqlQuery = "call proc_GetListRole()";
        List<RoleResponse> responseList = query(sqlQuery, new RoleResponseMapper());
        return responseList;
    }

    @Override
    public RoleDetailResponse getRoleDetail(int id) {
        String sqlQuery = "call proc_RoleDetail(?)";
        List<RoleDetailResponse> responseList = queryDetails(sqlQuery, new RoleDetailResponseMapper(), id);
        return responseList.get(0);
    }

    @Override
    public void removeRoleHasPermission(int roleId) {
        String sqlQuery = "update rolehaspermission " +
                "set isEnabled = 0 " +
                "where roleId  = ? ";
        update(sqlQuery, roleId);
    }

    @Override
    public void removeRole(int id) {
        String sqlQuery = "update role " +
                "set isEnabled = 0 " +
                "where id  = ? ";
        update(sqlQuery, id);
    }

    @Override
    public String getPermission(int id) {
        String sqlQuery = "select GROUP_CONCAT(permissionId) AS permissions " +
                            "from rolehaspermission " +
                            "where roleId = ? and isEnabled = true";
        List<String> response = query(sqlQuery, new ListPermissionByRoleMapper(), id);
        return response.get(0);
    }
}
