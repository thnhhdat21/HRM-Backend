package vn.tdsoftware.hrm_backend.dto.department.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequest {
    private Long id;
    private String name;
    private String code;
    private int level;
    private Long parentId;
    private Integer businessBlockId;
}
