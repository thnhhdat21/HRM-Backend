package vn.tdsoftware.hrm_backend.service;

public interface TokenService {
    void storeTokens(String username, String accessToken, String refreshToken);
    void storeAccessToken(String username, String accessToken);
    String getAccessToken(String username);
    String getRefreshToken(String username);
    void removeAllTokens(String username);
    void removeAccessToken(String username);
    boolean isAccessTokenBlacklisted(String token);
    boolean isRefreshTokenBlacklisted(String token);

}
