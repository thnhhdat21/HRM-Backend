package vn.tdsoftware.hrm_backend.dto.jobposition.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobPositionDetailResponse {
    private Long id;
    private String name;
    private long dutyId;
    private int roleId;
    private int salaryFrom;
    private int salaryTo;
    private String des;
}
