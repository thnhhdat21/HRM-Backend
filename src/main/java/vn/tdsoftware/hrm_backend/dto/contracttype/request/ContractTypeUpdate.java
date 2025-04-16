package vn.tdsoftware.hrm_backend.dto.contracttype.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContractTypeUpdate {
    private Long id;
    private String name;
    private String type;
    private Boolean insurance;
    private String method;
    private int term;
    private String unit;
    private List<Long> allowances;
    private boolean updateAllowance;
}
