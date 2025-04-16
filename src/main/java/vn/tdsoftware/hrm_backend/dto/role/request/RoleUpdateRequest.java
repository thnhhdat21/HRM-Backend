package vn.tdsoftware.hrm_backend.dto.role.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUpdateRequest {
    private int id;
    private String name;
    private String code;
    private String desc;
    private String permissions;
}
