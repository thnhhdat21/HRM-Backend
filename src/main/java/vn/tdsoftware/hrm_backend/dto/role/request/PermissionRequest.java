package vn.tdsoftware.hrm_backend.dto.role.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequest {
    private long roleHasPermissionId;
    private int permissionId;
    private String group;
    private String isUpdate;
}
