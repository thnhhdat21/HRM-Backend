package vn.tdsoftware.hrm_backend.dto.contracttype.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ContractTypeRequest {
    private Long id;
    private String name;
    private String type;
    private Boolean insurance;
    private String method;
    private int term;
    private String unit;
    private List<Long> allowances;
}
