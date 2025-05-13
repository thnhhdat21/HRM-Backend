package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.service.TokenService;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String ACCESS_KEY_PREFIX = "user:access:";
    private static final String REFRESH_KEY_PREFIX = "user:refresh:";
    private static final String ACCESS_BLACKLIST_PREFIX = "blacklist:access:";
    private static final String REFRESH_BLACKLIST_PREFIX = "blacklist:refresh:";
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.refreshExpiration}")
    private long refreshExpiration;

    //Lưu token vào redis
    @Override
    public void storeTokens(String username, String accessToken, String refreshToken){
        String accessKey = ACCESS_KEY_PREFIX + username;
        storeToken(accessKey, accessToken, jwtExpiration);

        String refreshKey = REFRESH_KEY_PREFIX + username;
        storeToken(refreshKey, refreshToken, refreshExpiration);
    }

    @Override
    public void storeAccessToken(String username, String accessToken) {
        String accessKey = ACCESS_KEY_PREFIX + username;
        storeToken(accessKey, accessToken, jwtExpiration);
    }

    private void storeToken(String key, String token, long expiration){
        redisTemplate.opsForValue().set(key, token);
        redisTemplate.expire(key, expiration, TimeUnit.MILLISECONDS);
    }

    //lấy lại access token cho user
    @Override
    public String getAccessToken(String username){
        String accessKey = ACCESS_KEY_PREFIX + username;
        return getToken(accessKey);
    }

    //lấy lại refresh token cho user
    @Override
    public String getRefreshToken(String username){
        String refreshKey = REFRESH_KEY_PREFIX + username;
        return getToken(refreshKey);
    }

    @Override
    public void removeAllTokens(String username){
        String accessToken = getAccessToken(username);
        String refreshToken = getRefreshToken(username);

        //Xóa token
        String accessKey = ACCESS_KEY_PREFIX + username;
        String refreshKey = REFRESH_KEY_PREFIX + username;
        redisTemplate.delete(accessKey);
        redisTemplate.delete(refreshKey);

        //BlackList token
        if (accessToken != null){
            String accessBlackListKey = ACCESS_BLACKLIST_PREFIX + accessToken;
            blacklistToken(accessBlackListKey, jwtExpiration);
        }

        if (refreshToken != null){
            String refreshBlackListKey = REFRESH_BLACKLIST_PREFIX + refreshToken;
            blacklistToken(refreshBlackListKey, refreshExpiration);
        }
    }

    @Override
    public void removeAccessToken(String username) {
        String accessToken = getAccessToken(username);
        String accessKey = ACCESS_KEY_PREFIX + username;
        redisTemplate.delete(accessKey);

        String accessBlackListKey = ACCESS_BLACKLIST_PREFIX + accessToken;
        blacklistToken(accessBlackListKey, jwtExpiration);
    }

    @Override
    public boolean isAccessTokenBlacklisted(String token){
        String key = ACCESS_BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public boolean isRefreshTokenBlacklisted(String token) {
        String key = REFRESH_BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    private String getToken(String tokenKey) {
        Object token = redisTemplate.opsForValue().get(tokenKey);
        return token != null ? token.toString() : null;
    }

    private void blacklistToken(String blackListKey, long expiration){
        redisTemplate.opsForValue().set(blackListKey, "blacklisted");
        redisTemplate.expire(blackListKey, expiration, TimeUnit.MILLISECONDS);
    }

}
