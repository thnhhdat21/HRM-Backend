package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.RoleDAO;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleDetailResponse;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleResponse;
import vn.tdsoftware.hrm_backend.entity.Role;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;
import vn.tdsoftware.hrm_backend.mapper.response.role.ListPermissionByRoleMapper;
import vn.tdsoftware.hrm_backend.mapper.response.role.RoleDetailResponseMapper;
import vn.tdsoftware.hrm_backend.mapper.response.role.RoleResponseMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public List<String> getRoleByUsername(String username) {
        String sql = "select role.code " +
                    " from account " +
                    " inner join contractgeneral on account.employeeId = contractGeneral.employeeId " +
                    " inner join jobposition on contractGeneral.jobPositionId = jobPosition.id " +
                    " inner join role on jobPosition.roleId = role.id " +
                     "where account.username = '" + username + "'";
        return query(sql, resultSet -> {
            try {
                return resultSet.getString("code");
            } catch (Exception e) {
               throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
            }
        });
    }

    @Override
    public List<Boolean> isRoleAdminByUsername(String username) {
        String sql = "select role.accountAdmin " +
                " from account " +
                " inner join contractgeneral on account.employeeId = contractGeneral.employeeId " +
                " inner join jobposition on contractGeneral.jobPositionId = jobPosition.id " +
                " inner join role on jobPosition.roleId = role.id " +
                "where account.username = '" + username + "'";
        return query(sql, resultSet -> {
            try {
                return resultSet.getBoolean("accountAdmin");
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
            }
        });
    }
}
