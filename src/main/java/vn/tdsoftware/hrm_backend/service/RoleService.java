package vn.tdsoftware.hrm_backend.service;


import vn.tdsoftware.hrm_backend.dto.role.request.RoleRequest;
import vn.tdsoftware.hrm_backend.dto.role.request.RoleUpdateRequest;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleDetailResponse;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getListRole();
    RoleDetailResponse getRoleDetail(int id);
    String createRole(RoleRequest roleRequest);
    String updateRole(RoleUpdateRequest roleUpdateRequest);
    String updateRoleNoUpdatePermission(RoleUpdateRequest roleUpdateRequest);
    void deleteRole(int id);
    String getPermission(int id);
}