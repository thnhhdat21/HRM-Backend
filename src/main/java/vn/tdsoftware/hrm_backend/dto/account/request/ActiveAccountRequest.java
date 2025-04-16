package vn.tdsoftware.hrm_backend.dto.account.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveAccountRequest {
    private String employeeCode;
    private String password;
}
