package vn.tdsoftware.hrm_backend.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.tdsoftware.hrm_backend.dto.auth.request.LoginRequest;
import vn.tdsoftware.hrm_backend.dto.auth.response.TokenResponse;

public interface AuthenticationService {
    TokenResponse authenticate(LoginRequest loginRequest);
    TokenResponse refreshToken(HttpServletRequest request);
    void logout(HttpServletRequest request);
}
