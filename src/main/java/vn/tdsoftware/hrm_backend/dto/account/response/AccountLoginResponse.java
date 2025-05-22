package vn.tdsoftware.hrm_backend.dto.account.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AccountLoginResponse {
    private long employeeId;
    private String employeeName;
    private String jobPosition;
    private List<String> permissions;
}
