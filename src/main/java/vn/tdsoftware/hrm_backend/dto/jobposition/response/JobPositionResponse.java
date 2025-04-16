package vn.tdsoftware.hrm_backend.dto.jobposition.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobPositionResponse {
    private long id;
    private String name;
    private String role;
    private int salaryFrom;
    private int salaryTo;
    private String status;
    private String createBy;
    private String des;
}
