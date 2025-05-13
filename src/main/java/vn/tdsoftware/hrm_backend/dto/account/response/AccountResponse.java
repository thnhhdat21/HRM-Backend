package vn.tdsoftware.hrm_backend.dto.account.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class AccountResponse {
    private Long id;
    private String username;
    private String role;
    private String employeeCode;
    private Long employeeId;
    private String fullName;
    private String department;
    private Date createDate;
    private Date activeDate;
}
