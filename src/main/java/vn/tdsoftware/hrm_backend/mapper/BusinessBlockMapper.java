package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.businessblock.response.BusinessBlockResponse;
import vn.tdsoftware.hrm_backend.entity.BusinessBlock;

public class BusinessBlockMapper {

    public static BusinessBlockResponse mapToBusinessBlockResponse(BusinessBlock businessBlock) {
        return BusinessBlockResponse.builder()
                .id(businessBlock.getId())
                .name(businessBlock.getName())
                .code(businessBlock.getCode())
                .build();
    }
}
