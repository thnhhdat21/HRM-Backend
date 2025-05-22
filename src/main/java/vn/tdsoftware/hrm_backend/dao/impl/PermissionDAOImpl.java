package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.PermissionDAO;
import vn.tdsoftware.hrm_backend.entity.Permission;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;

import java.util.List;

@Component
public class PermissionDAOImpl extends AbstractDao<Permission> implements PermissionDAO {

    @Override
    public List<String> getPermissionByRole(String role) {
        String sql = "select permission.name  " +
                "from account  " +
                "inner join contractgeneral on account.employeeId = contractGeneral.employeeId  " +
                "inner join jobposition on contractGeneral.jobPositionId = jobPosition.id  " +
                "inner join role on jobPosition.roleId = role.id  " +
                "inner join rolehaspermission on role.id = rolehasPermission.roleId and roleHasPermission.isEnabled = 1 " +
                "inner join permission on rolehasPermission.permissionId = permission.id " +
                "where role.code = '" + role + "'";
        return query(sql, resultSet -> {
            try {
                return resultSet.getString("name");
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
            }
        });
    }

    @Override
    public void deleteAllById(int id) {
        String sql = "UPDATE RoleHasPermission e SET e.isEnabled = false WHERE e.roleId = ?";
        update(sql, id);
    }

    @Override
    public void deleteCustomById(int id) {
        String sql = "UPDATE RoleHasPermission e INNER JOIN Permission p ON e.permissionId = p.id SET e.isEnabled = false WHERE e.roleId = ? and p.groupPer != 'default'";
        update(sql, id);
    }
}
