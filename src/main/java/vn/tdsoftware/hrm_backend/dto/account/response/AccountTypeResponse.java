package vn.tdsoftware.hrm_backend.dto.account.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountTypeResponse {
    private int accountActive;
    private int accountNotActive;
    private int accountLock;
}
