package vn.tdsoftware.hrm_backend.dto.contracttype.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CountContractTypeResponse {
    private long id;
    private String name;
    private int count;
}
