package vn.tdsoftware.hrm_backend.dto.jobposition.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPositionRequest {
    private Long id;
    private String name;
    private long dutyId;
    private int roleId;
    private int salaryFrom;
    private int salaryTo;
    private String des;
}
