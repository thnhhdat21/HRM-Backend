package vn.tdsoftware.hrm_backend.dto.account.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountCountResponse {
    private int accountActive;
    private int accountNotActive;
    private int accountLock;
}
