package vn.tdsoftware.hrm_backend.dto.role.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleUpdateRequest {
    private int id;
    private String name;
    private String code;
    private String desc;
    private String role;
    private List<PermissionRequest> permissions;
}
