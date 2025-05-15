package vn.tdsoftware.hrm_backend.dto.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountLoginResponse;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Builder
public class TokenResponse implements Serializable {
    private String accessToken;
    private String refreshToken;
    private AccountLoginResponse account;
}