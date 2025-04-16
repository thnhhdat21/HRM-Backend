package vn.tdsoftware.hrm_backend.dto.account.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class AccountDetailResponse {
    private long id;
    private String name;
    private String username;
    private String department;
    private String email;
    private Date dateOfBirth;
    private String phoneNumber;
    private int status;
    private int roleId;
    private String permissions;
}
