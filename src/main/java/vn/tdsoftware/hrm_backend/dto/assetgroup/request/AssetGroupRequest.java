package vn.tdsoftware.hrm_backend.dto.assetgroup.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetGroupRequest {
    private Long id;
    private String name;
    private Long parentId;
}
