package vn.tdsoftware.hrm_backend.dto.allowance.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AllowanceRequest {
    private Long id;
    private String name;
    private int amount;
    private String unit;
}
