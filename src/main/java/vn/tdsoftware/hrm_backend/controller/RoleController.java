package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.role.request.RoleRequest;
import vn.tdsoftware.hrm_backend.dto.role.request.RoleUpdateRequest;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleDetailResponse;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleResponse;
import vn.tdsoftware.hrm_backend.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/list-role")
    public ResponseData<List<RoleResponse>> listRole() {
        List<RoleResponse> response = roleService.getListRole();
        return ResponseData.<List<RoleResponse>>builder()
                .code(1000)
                .message("Create role successfully")
                .data(response)
                .build();
    }

    @PostMapping("/role-detail")
    public ResponseData<RoleDetailResponse> getRoleDetail(@RequestParam("id") int id) {
        RoleDetailResponse response = roleService.getRoleDetail(id);
        return ResponseData.<RoleDetailResponse>builder()
                .code(1000)
                .message("Create role successfully")
                .data(response).build();
    }

    @PostMapping("/create-role")
    public ResponseData<String> createRole(@RequestBody RoleRequest roleRequest) {
        String response = roleService.createRole(roleRequest);
        return ResponseData.<String>builder()
                .code(1000)
                .message("Create role successfully")
                .data(response).build();
    }

    @PostMapping("/update-role")
    public ResponseData<String> updateRole(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        String response = roleService.updateRole(roleUpdateRequest);
        return ResponseData.<String>builder()
                .code(1000)
                .message("Create role successfully")
                .data(response).build();
    }

    @PostMapping("/update-role-no-update-permission")
    public ResponseData<String> updateRoleNoUpdatePermission(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        String response = roleService.updateRoleNoUpdatePermission(roleUpdateRequest);
        return ResponseData.<String>builder()
                .code(1000)
                .message("Create role successfully")
                .data(response).build();
    }

    @PostMapping("/delete-role")
    public ResponseData<Void> deleteRole(@RequestParam("id") int id) {
        roleService.deleteRole(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete role successfully")
                .build();
    }

    @PostMapping("/get-permission")
    public ResponseData<String> getPermission(@RequestParam("id") int id) {
        String response = roleService.getPermission(id);
        return ResponseData.<String>builder()
                .code(1000)
                .message("Create role successfully")
                .data(response).build();
    }
}
