package vn.tdsoftware.hrm_backend.dto.assetunit.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetUnitResponse {
    private long id;
    private String name;
    private String status;
}
