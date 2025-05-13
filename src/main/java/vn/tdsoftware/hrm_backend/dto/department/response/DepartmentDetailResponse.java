package vn.tdsoftware.hrm_backend.dto.department.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDetailResponse {
    private Long id;
    private String name;
    private String code;
    private Integer businessBlockId;
    private Integer departmentLevel;
    private Long parentId;
}
