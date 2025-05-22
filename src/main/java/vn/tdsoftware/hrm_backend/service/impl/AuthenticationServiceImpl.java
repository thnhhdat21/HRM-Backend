package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountLoginResponse;
import vn.tdsoftware.hrm_backend.dto.auth.request.LoginRequest;
import vn.tdsoftware.hrm_backend.dto.auth.response.TokenResponse;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeResponse;
import vn.tdsoftware.hrm_backend.entity.Account;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.AccountRepository;
import vn.tdsoftware.hrm_backend.service.AuthenticationService;
import vn.tdsoftware.hrm_backend.service.EmployeeService;
import vn.tdsoftware.hrm_backend.service.JwtService;
import vn.tdsoftware.hrm_backend.service.TokenService;
import vn.tdsoftware.hrm_backend.util.constant.AccountConstant;

import java.util.ArrayList;
import java.util.List;

import static vn.tdsoftware.hrm_backend.util.TokenType.REFRESH_TOKEN;
import static vn.tdsoftware.hrm_backend.util.constant.RoleConstant.ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    public TokenResponse authenticate(LoginRequest loginRequest) {
        try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.USERNAME_OR_PASSWORD_IS_EXIST);
        }

        var account =  accountRepository.findByUsernameAndIsEnabled(loginRequest.getUsername(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.USERNAME_OR_PASSWORD_IS_EXIST));

        if (account.getStatus() == AccountConstant.ACCOUNT_NOT_ACTIVE) {
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_ACTIVE);
        } else if (account.getStatus() == AccountConstant.ACCOUNT_LOCK) {
            throw new BusinessException(ErrorCode.ACCOUNT_LOCKED);
        }

        // Tạo accessToken và refreshToken
        String accessToken = jwtService.generateToken(account);
        String refreshToken = jwtService.generateRefreshToken(account);
        Boolean isAdmin = jwtService.isAdmin(accessToken);
        List<String> permissions = new ArrayList<>();
        if (isAdmin) {
            permissions.add(ADMIN);
        } else {
            permissions = jwtService.extractPermissions(accessToken);
        }
        //Lưu token vào redis
        tokenService.storeTokens(account.getUsername(), accessToken, refreshToken);

        EmployeeResponse employeeJob = employeeService.getJobPositionByEmployeeId(account.getEmployeeId());

        AccountLoginResponse accountLogin = AccountLoginResponse.builder()
                .employeeId(account.getEmployeeId())
                .employeeName(employeeJob.getFullName())
                .jobPosition(employeeJob.getJobPosition())
                .permissions(permissions)
                .build();

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .account(accountLogin)
                .build();
    }

    @Override
    public TokenResponse refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("x-token");
        if (StringUtils.isBlank((refreshToken))){
            throw new BusinessException(ErrorCode.TOKEN_IS_EMPTY);
        }
        // extract user
        final String username = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);
        // check với db
        Account account = accountRepository.findByUsernameAndIsEnabled(username, true).orElseThrow(
                () -> new BusinessException(ErrorCode.ACCOUNT_EXIST));

        if (!jwtService.isValid(refreshToken,REFRESH_TOKEN, account)) {
            throw new BusinessException(ErrorCode.TOKEN_IN_VALID);
        }

        if (tokenService.isRefreshTokenBlacklisted(refreshToken)) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_IN_BLACKLIST);
        }

        String storedRefreshToken = tokenService.getRefreshToken(username);
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_IN_VALID);
        }

        String accessToken = jwtService.generateToken(account);

        tokenService.storeAccessToken(username, accessToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("x-token");
        if (StringUtils.isBlank((refreshToken))){
            throw new BusinessException(ErrorCode.TOKEN_IS_EMPTY);
        }
        String username = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);
        tokenService.removeAllTokens(username);
    }
}
