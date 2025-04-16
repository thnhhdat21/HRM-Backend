package vn.tdsoftware.hrm_backend.dto.contracttype.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractTypeDetail {
    private long id;
    private String name;
    private String type;
    private String method;
    private Integer term;
    private String unit;
    private boolean insurance;
    private String allowances;
}
