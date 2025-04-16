package vn.tdsoftware.hrm_backend.dto.role.response;

import lombok.*;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse implements Serializable {
    private int id;
    private String name;
    private boolean accountAdmin;
    private boolean accountDefault;
    private int count;
    private String createdBy;
    private String updatedBy;
    private Date createdDate;
    private Date updatedDate;
}
