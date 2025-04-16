package vn.tdsoftware.hrm_backend.dto.businessblock.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BusinessBlockResponse {
    private long id;
    private String name;
    private String code;
}
