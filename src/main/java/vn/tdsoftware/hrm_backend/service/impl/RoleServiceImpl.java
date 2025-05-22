package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.PermissionDAO;
import vn.tdsoftware.hrm_backend.dao.RoleDAO;
import vn.tdsoftware.hrm_backend.dto.role.request.PermissionRequest;
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

import java.util.*;
import java.util.stream.Stream;

import static vn.tdsoftware.hrm_backend.util.ConvertUtil.permissionValidator;
import static vn.tdsoftware.hrm_backend.util.constant.PermissionConstant.*;
import static vn.tdsoftware.hrm_backend.util.constant.UpdateTypeConstant.DELETE;
import static vn.tdsoftware.hrm_backend.util.constant.UpdateTypeConstant.UPDATE;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleHasPermissionRepository roleHasPermissionRepository;
    private final RoleDAO roleDAO;
    private final PermissionDAO permissionDAO;

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
        List<Integer> listPermission = new ArrayList<>();
        List<Integer> listPerDefault;
        listPerDefault = ConvertUtil.permissionsDefault;
        if(!ADMIN.equals(roleRequest.getPermissions()) && !DEFAULT.equals(roleRequest.getPermissions())) {
            listPermission = permissionValidator(roleRequest.getPermissions());
        }
        Role roleSaved = roleRepository.save(Role.builder()
                .name(roleRequest.getName())
                .code(roleRequest.getCode())
                .description(roleRequest.getDesc())
                .accountAdmin(Objects.equals(roleRequest.getPermissions(), ADMIN))
                .accountDefault(Objects.equals(roleRequest.getPermissions(), PermissionConstant.DEFAULT))
                .build());

        if (!ADMIN.equals(roleRequest.getPermissions())) {
            List<Integer> ALL_PERMISSION = Stream.concat(
                    listPerDefault.stream(),
                    listPermission.stream()
            ).distinct().toList();

            for (Integer permission : ALL_PERMISSION) {
                roleHasPermissionRepository.save(RoleHasPermission.builder()
                        .roleId(roleSaved.getId())
                        .permissionId(permission)
                        .build());
            }
        }
        return roleSaved.getName();
    }

    @Override
    @Transactional
    public void updateRole(RoleUpdateRequest roleUpdateRequest) {
        FieldStringUtil.validatorNameAndCode(roleUpdateRequest.getName(), roleUpdateRequest.getCode());
        Role role = roleRepository.findByIdAndIsEnabled(roleUpdateRequest.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.ROLE_NOT_EXISTED)
        );
        role.setName(roleUpdateRequest.getName());
        role.setCode(roleUpdateRequest.getCode());
        role.setDescription(roleUpdateRequest.getDesc());
        if(ADMIN.equals(roleUpdateRequest.getRole())) {
            role.setAccountDefault(false);
            role.setAccountAdmin(true);
            permissionDAO.deleteCustomById(roleUpdateRequest.getId());
        } else if (DEFAULT.equals(roleUpdateRequest.getRole())) {
            role.setAccountAdmin(false);
            role.setAccountDefault(true);
            permissionDAO.deleteCustomById(roleUpdateRequest.getId());
        } else {
            role.setAccountAdmin(false);
            role.setAccountDefault(false);
            List<RoleHasPermission> roleSave = new ArrayList<>();
            for (PermissionRequest request : roleUpdateRequest.getPermissions()) {
                if(DELETE.equals(request.getIsUpdate()) || request.getPermissionId() == PERMISSION_DELETE) {
                    RoleHasPermission roleHasPermission = roleHasPermissionRepository.findByIdAndIsEnabled(request.getRoleHasPermissionId(), true)
                            .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_EXISTED));
                    roleHasPermission.setEnabled(false);
                } else if(UPDATE.equals(request.getIsUpdate())) {
                    RoleHasPermission roleHasPermission = roleHasPermissionRepository.findByIdAndIsEnabled(request.getRoleHasPermissionId(), true)
                            .orElse(new RoleHasPermission());
                    roleHasPermission.setRoleId(role.getId());
                    roleHasPermission.setPermissionId(request.getPermissionId());
                    roleSave.add(roleHasPermission);
                }
            }
            if (!roleSave.isEmpty()) {
                roleHasPermissionRepository.saveAll(roleSave);
            }
        }
        roleRepository.save(role);
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
}