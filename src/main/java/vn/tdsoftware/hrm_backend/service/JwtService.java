package vn.tdsoftware.hrm_backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.tdsoftware.hrm_backend.util.TokenType;

import java.util.List;

public interface JwtService {
    String generateToken(UserDetails user);
    String generateRefreshToken(UserDetails user);
    String extractUsername(String token, TokenType type);
    List<String> extractPermissions(String token);
    List<String> extractRoles(String token);
    Long extractDepartment(String token);
    Long extractEmployeeId(String token);
    boolean isValid(String token, TokenType type, UserDetails user);
}
