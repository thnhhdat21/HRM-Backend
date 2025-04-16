package vn.tdsoftware.hrm_backend.dto.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleDTO {
    private int id;
    private String name;
    private String code;
    private boolean accountAdmin;
    private boolean accountDefault;
    private String description;
}
