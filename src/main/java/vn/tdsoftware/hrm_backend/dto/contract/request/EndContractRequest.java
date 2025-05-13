package vn.tdsoftware.hrm_backend.dto.contract.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EndContractRequest {
    private long contractId;
    private LocalDate dateLiquidation;
    private String reasonLiquidation;
}
