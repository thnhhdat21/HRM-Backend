package vn.tdsoftware.hrm_backend.dto.role.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PermissionResponse {
    private long roleHasPermissionId;
    private int permissionId;
    private String group;
}
