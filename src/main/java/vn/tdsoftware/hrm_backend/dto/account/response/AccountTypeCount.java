package vn.tdsoftware.hrm_backend.dto.account.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountTypeCount {
    private int statusId;
    private int count;
}
