package vn.tdsoftware.hrm_backend.dto.contract.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class WorkProcessResponse {
    private String code;
    private Date dateStart;
    private Date dateSign;
    private int status;
    private String department;
    private String jobPosition;
    private String duty;
    private String contractType;
}
