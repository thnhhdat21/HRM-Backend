package vn.tdsoftware.hrm_backend.controller;

import io.minio.messages.ResponseDate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.auth.request.LoginRequest;
import vn.tdsoftware.hrm_backend.dto.auth.response.TokenResponse;
import vn.tdsoftware.hrm_backend.service.AuthenticationService;
import vn.tdsoftware.hrm_backend.service.UserService;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseData<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse response = authenticationService.authenticate(request);
        return  ResponseData.<TokenResponse>builder()
                .code(1000)
                .data(response)
                .message("login successfuly")
                .build();
    }

    @PostMapping("/refresh")
    public ResponseData<TokenResponse> refresh(HttpServletRequest request) {
        TokenResponse response = authenticationService.refreshToken(request);
        return  ResponseData.<TokenResponse>builder()
                .code(1000)
                .data(response)
                .message("login successfuly")
                .build();
    }

    @PostMapping("/logout")
    public ResponseData<Void> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return  ResponseData.<Void>builder()
                .code(1000)
                .message("login successfuly")
                .build();
    }
}
