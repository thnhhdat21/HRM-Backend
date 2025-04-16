package vn.tdsoftware.hrm_backend.dto.role.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    private String name;
    private String code;
    private String desc;
    private String permissions;
}
