package vn.tdsoftware.hrm_backend.dto.rolehaspermission.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleHasPermissionResponse {
    private int roleHasPermissionId;
    private int permissionId;
}
