package vn.tdsoftware.hrm_backend.dto.department.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class DepartmentTreeResponse {
    private Long id;
    private String name;
    private String departmentLevel;
    private String businessBlock;
    private Long parentId;
    private List<DepartmentTreeResponse> children;
}
