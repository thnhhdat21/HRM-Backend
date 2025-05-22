package vn.tdsoftware.hrm_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.tdsoftware.hrm_backend.dto.account.response.CurrentAccountDTO;
import vn.tdsoftware.hrm_backend.entity.Account;
import vn.tdsoftware.hrm_backend.entity.Role;
import vn.tdsoftware.hrm_backend.entity.RoleHasPermission;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.RoleRepository;
import vn.tdsoftware.hrm_backend.service.JwtService;
import vn.tdsoftware.hrm_backend.service.TokenService;
import vn.tdsoftware.hrm_backend.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static vn.tdsoftware.hrm_backend.util.TokenType.ACCESS_TOKEN;
import static vn.tdsoftware.hrm_backend.util.constant.RoleConstant.ADMIN;

@Component
@Slf4j
@RequiredArgsConstructor
public class PreFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("---------------------PreFilter-------------------");
        try {
            final String authorization = request.getHeader("Authorization");
            log.info("Authorization {}", authorization);

            if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            final String token = authorization.substring("Bearer ".length());

            if (tokenService.isAccessTokenBlacklisted(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String username = jwtService.extractUsername(token, ACCESS_TOKEN);
            Boolean isAdmin = jwtService.isAdmin(token);
            List<String> permissions = new ArrayList<>();
            if (isAdmin) {
                permissions.add(ADMIN);
            } else {
                permissions = jwtService.extractPermissions(token);
            }

            Long departmentId = jwtService.extractDepartment(token);
            List<GrantedAuthority> authorities = permissions
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            log.info("UserInfo: username: {} \nPermission: {}", username, authorities);
            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
                if (jwtService.isValid(token, ACCESS_TOKEN, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);

                    // set cho user Hien tai
                    CurrentAccountDTO.create(((Account) userDetails).getEmployeeId(), departmentId ,permissions);
                    log.info("CurrentAccountDTO: {}", CurrentAccountDTO.getEmployeeId());
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.warn("Token đã hết hạn!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("application/json");
            response.getWriter().write("{\"code\": " + ErrorCode.TOKEN_EXPIRED.getCode() + ",\n\"error\": \"Token expired\"}");
        }
    }
}
