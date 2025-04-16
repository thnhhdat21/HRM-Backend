package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.entity.Allowance;

public class AllowanceMapper {

    public static AllowanceResponse mapToAllowanceResponse(Allowance allowance) {
        return AllowanceResponse.builder()
                .id(allowance.getId())
                .name(allowance.getName())
                .amount(allowance.getAmount())
                .unit(allowance.getUnit())
                .status(allowance.getStatus())
                .build();
    }
}
