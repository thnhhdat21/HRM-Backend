package vn.tdsoftware.hrm_backend.dto.role.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vn.tdsoftware.hrm_backend.dto.rolehaspermission.response.RoleHasPermissionResponse;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDetailResponse {
    private int id;
    private String name;
    private String code;
    private String description;
    private boolean accountAdmin;
    private boolean accountDefault;
    private List<PermissionResponse> permissions;
}
