package vn.tdsoftware.hrm_backend.dto.permission.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse implements Serializable {
    private String name;
}
