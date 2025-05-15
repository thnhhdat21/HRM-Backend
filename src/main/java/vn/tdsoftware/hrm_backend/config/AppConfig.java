package vn.tdsoftware.hrm_backend.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.tdsoftware.hrm_backend.service.UserService;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class AppConfig {
    private final String[] WHITE_LIST = {"/auth/**", "/file/get-file"};
    private final String[] WHITE_ITEM_MANAGE = {"/duty/**", "/department/**", "/allowance/**", "/job-position/**", "/letter-reason/**"};

        private final String[] ROLES_WATCH_COMPANY = {"ROLE_WATCH_EMPLOYEE_COMPANY",
        "ROLE_WATCH_CONTRACT_COMPANY",
        "ROLE_WATCH_DECISION_COMPANY",
        "ROLE_WATCH_TIMESHEET_COMPANY",
        "ROLE_WATCH_SALARY_COMPANY",
        "ROLE_WATCH_LETTER_COMPANY",
        "ROLE_WATCH_INSURANCE_COMPANY"};

    private final String[] ROLES_WATCH_DEPARTMENT = {"ROLE_WATCH_EMPLOYEE_DEPARTMENT",
            "ROLE_WATCH_CONTRACT_DEPARTMENT",
            "ROLE_WATCH_DECISION_DEPARTMENT",
            "ROLE_WATCH_TIMESHEET_DEPARTMENT",
            "ROLE_WATCH_SALARY_DEPARTMENT",
            "ROLE_WATCH_LETTER_DEPARTMENT",
            "ROLE_WATCH_INSURANCE_DEPARTMENT"};

    String[] ALL_ROLES = Stream.concat(
            Arrays.stream(ROLES_WATCH_COMPANY),
            Arrays.stream(ROLES_WATCH_DEPARTMENT)
    ).toArray(String[]::new);

    private final UserService userService;

    private final PreFilter preFilter;

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*") // Allowed request headers
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer :: disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(WHITE_ITEM_MANAGE).hasAnyAuthority(ALL_ROLES)
                        .requestMatchers("/contract-type/**").hasAnyAuthority("ADMIN", "ROLE_MANAGE_CONTRACT")
                        .requestMatchers("/employee/get-list-employee").hasAnyAuthority("ADMIN", "ROLE_MANAGE_CONTRACT")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(provider()).addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager  authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService.userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
}
