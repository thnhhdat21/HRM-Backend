package vn.tdsoftware.hrm_backend.dto.employee.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse {
    private Long id;
    private String code;
    private String fullName;
    private String department;
    private String jobPosition;
    private String duty;
    private Date dateJoin;
    private Date dateOfBirth;
    private String gender;
    private Integer accountStatus;
    private Long accountId;
    private Long contractId;
}
