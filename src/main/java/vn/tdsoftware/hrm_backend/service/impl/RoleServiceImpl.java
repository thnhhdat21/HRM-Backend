package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.RoleDAO;
import vn.tdsoftware.hrm_backend.dto.role.request.RoleRequest;
import vn.tdsoftware.hrm_backend.dto.role.request.RoleUpdateRequest;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleDetailResponse;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleResponse;
import vn.tdsoftware.hrm_backend.entity.Role;
import vn.tdsoftware.hrm_backend.entity.RoleHasPermission;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.RoleHasPermissionRepository;
import vn.tdsoftware.hrm_backend.repository.RoleRepository;
import vn.tdsoftware.hrm_backend.service.RoleService;
import vn.tdsoftware.hrm_backend.util.ConvertUtil;
import vn.tdsoftware.hrm_backend.util.FieldStringUtil;
import vn.tdsoftware.hrm_backend.util.constant.PermissionConstant;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static vn.tdsoftware.hrm_backend.util.ConvertUtil.permissionValidator;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleHasPermissionRepository roleHasPermissionRepository;
    private final RoleDAO roleDAO;

    @Override
    public List<RoleResponse> getListRole() {
        List<RoleResponse> responseList = roleDAO.getListRole();
        return responseList;
    }

    @Override
    public RoleDetailResponse getRoleDetail(int id) {
        RoleDetailResponse response = roleDAO.getRoleDetail(id);
        if (response == null) {
            throw new BusinessException(ErrorCode.ROLE_NOT_EXISTED);
        }
        return response;
    }

    @Override
    @Transactional
    public String createRole(RoleRequest roleRequest) {
        FieldStringUtil.validatorNameAndCode(roleRequest.getName(), roleRequest.getCode());
        List<Integer> listPermission;
        if (PermissionConstant.ADMIN.contains(roleRequest.getPermissions())) {
            listPermission = ConvertUtil.permissionsAdmin;
        } else if (PermissionConstant.DEFAULT.contains(roleRequest.getPermissions())) {
            listPermission = ConvertUtil.permissionsDefault;
        } else {
            listPermission = permissionValidator(roleRequest.getPermissions());
        }
        Role roleSaved = roleRepository.save(Role.builder()
                .name(roleRequest.getName())
                .code(roleRequest.getCode())
                .description(roleRequest.getDesc())
                .accountAdmin(Objects.equals(roleRequest.getPermissions(), PermissionConstant.ADMIN))
                .accountDefault(Objects.equals(roleRequest.getPermissions(), PermissionConstant.DEFAULT))
                .build());
        for (Integer permission : listPermission) {
            roleHasPermissionRepository.save(RoleHasPermission.builder()
                            .roleId(roleSaved.getId())
                            .permissionId(permission)
                    .build());
        }
        return roleSaved.getName();
    }

    @Override
    @Transactional
    public String updateRole(RoleUpdateRequest roleUpdateRequest) {
        FieldStringUtil.validatorNameAndCode(roleUpdateRequest.getName(), roleUpdateRequest.getCode());
        List<Integer> listPermission;
        if (PermissionConstant.ADMIN.contains(roleUpdateRequest.getPermissions())) {
            listPermission = ConvertUtil.permissionsAdmin;
        } else if (PermissionConstant.DEFAULT.contains(roleUpdateRequest.getPermissions())) {
            listPermission = ConvertUtil.permissionsDefault;
        } else {
            listPermission = permissionValidator(roleUpdateRequest.getPermissions());
        }
        Role roleSaved = updateRoleGeneral(roleUpdateRequest);
        roleDAO.removeRoleHasPermission(roleUpdateRequest.getId());
        for (Integer permission : listPermission) {
            roleHasPermissionRepository.save(RoleHasPermission.builder()
                    .roleId(roleSaved.getId())
                    .permissionId(permission)
                    .build());
        }
        return roleSaved.getName();
    }

    @Override
    public String updateRoleNoUpdatePermission(RoleUpdateRequest roleUpdateRequest) {
        FieldStringUtil.validatorNameAndCode(roleUpdateRequest.getName(), roleUpdateRequest.getCode());
        Role roleSaved = updateRoleGeneral(roleUpdateRequest);
        return roleSaved.getName();
    }

    @Override
    public void deleteRole(int id) {
        roleDAO.removeRole(id);
        roleDAO.removeRoleHasPermission(id);
    }

    @Override
    public String getPermission(int id) {
        return roleDAO.getPermission(id);
    }

    private Role updateRoleGeneral(RoleUpdateRequest roleUpdateRequest) {
        Optional<Role> roleEntity = roleRepository.findById(roleUpdateRequest.getId());
        if (roleEntity.isEmpty()) {
            throw new BusinessException(ErrorCode.ROLE_NOT_EXISTED);
        }
        roleEntity.get().setName(roleUpdateRequest.getName());
        roleEntity.get().setCode(roleUpdateRequest.getCode());
        roleEntity.get().setDescription(roleUpdateRequest.getDesc());
        roleEntity.get().setAccountAdmin(PermissionConstant.ADMIN.contains(roleUpdateRequest.getPermissions()));
        roleEntity.get().setAccountDefault(PermissionConstant.DEFAULT.contains(roleUpdateRequest.getPermissions()));
        return roleRepository.save(roleEntity.get());
    }
}