package vn.tdsoftware.hrm_backend.dto.duty.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DutyResponse {
    private long id;
    private String name;
    private String description;
    private String status;
    private String createdBy;
}
