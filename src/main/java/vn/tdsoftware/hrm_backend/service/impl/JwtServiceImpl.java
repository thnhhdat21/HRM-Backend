package vn.tdsoftware.hrm_backend.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.dao.PermissionDAO;
import vn.tdsoftware.hrm_backend.dao.RoleDAO;
import vn.tdsoftware.hrm_backend.entity.Account;
import vn.tdsoftware.hrm_backend.entity.ContractGeneral;
import vn.tdsoftware.hrm_backend.repository.ContractGeneralRepository;
import vn.tdsoftware.hrm_backend.repository.RoleRepository;
import vn.tdsoftware.hrm_backend.service.JwtService;
import vn.tdsoftware.hrm_backend.util.TokenType;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import static vn.tdsoftware.hrm_backend.util.TokenType.ACCESS_TOKEN;
import static vn.tdsoftware.hrm_backend.util.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiryHour}")
    private long expiryTime;

    @Value("${jwt.expiryDay}")
    private long expiryDay;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    private final RoleDAO roleDAO;

    private final RoleRepository roleRepository;

    private final PermissionDAO permissionDAO;

    private final ContractGeneralRepository contractGeneralRepository;

    @Override
    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return generateRefreshToken(new HashMap<>(), user);
    }

    @Override
    public String extractUsername(String token, TokenType type) {
        return extractClaim(token, type, Claims::getSubject);
    }

    @Override
    public List<String> extractPermissions(String token) {
        List<String> roles = extractRole(token);
        return permissionDAO.getPermissionByRole(roles.get(0));
    }

    @Override
    public List<String> extractRoles(String token) {
        return extractRole(token);
    }

    @Override
    public Boolean isAdmin(String token) {
        return extractIsAdmin(token);
    }

    @Override
    public Long extractDepartment(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey(ACCESS_TOKEN))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("departmentId", Long.class);
    }

    @Override
    public Long extractEmployeeId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey(ACCESS_TOKEN))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("employeeId", Long.class);
    }

    @Override
    public boolean isValid(String token, TokenType type,  UserDetails userDetails) {
        final String username = extractUsername(token, type);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, type));
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        List<String> roleNames = roleDAO.getRoleByUsername(userDetails.getUsername());
        List<Boolean> isAdmin = roleDAO.isRoleAdminByUsername(userDetails.getUsername());
        Optional<ContractGeneral> contractGeneral = contractGeneralRepository.findByEmployeeId(((Account) userDetails).getEmployeeId());
        contractGeneral.ifPresent(general -> claims.put("departmentId", general.getDepartmentId()));
        claims.put("roles", roleNames);
        claims.put("isAdmin", isAdmin.get(0));
        claims.put("employeeId",((Account) userDetails).getEmployeeId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryTime ))
                .signWith(getKey(ACCESS_TOKEN),SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryDay))
                .signWith(getKey(REFRESH_TOKEN),SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType type) {
        byte[] keyBytes;
        if (ACCESS_TOKEN.equals(type)) {
            keyBytes = Decoders.BASE64.decode(secretKey);
        } else {
            keyBytes = Decoders.BASE64.decode(refreshKey);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaim(String token, TokenType type,  Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaim(token, type);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaim(String token, TokenType type) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(type)).build()
                .parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token, TokenType type) {
        return extractExpiration(token, type).before(new Date());
    }

    private Date extractExpiration(String token, TokenType type) {
        return extractClaim(token, type, Claims::getExpiration);
    }

    private List<String> extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey(ACCESS_TOKEN))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles", List.class);
    }

    private Boolean extractIsAdmin(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey(ACCESS_TOKEN))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("isAdmin", Boolean.class);
    }
}
