package vn.tdsoftware.hrm_backend.dto.allowance.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowanceResponse {
    private Long id;
    private String name;
    private Integer amount;
    private String unit;
    private String status;
}
