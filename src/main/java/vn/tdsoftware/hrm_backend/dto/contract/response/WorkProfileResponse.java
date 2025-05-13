package vn.tdsoftware.hrm_backend.dto.contract.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class WorkProfileResponse {
    private long employeeId;
    private String employeeCode;
    private String employeeName;
    private int status;
    private int type;
    private String department;
    private String jobPosition;
    private String duty;
    private Date dateJoin;
    private Date dateSign;
    private Date dateOnBoard;
    private String contractName;
    private int salaryGross;
    private String account;
    private String role;
}
