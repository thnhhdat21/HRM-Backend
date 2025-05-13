package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.repository.AccountRepository;
import vn.tdsoftware.hrm_backend.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> accountRepository.findByUsernameAndIsEnabled(username, true).orElseThrow(() -> new UsernameNotFoundException("Không thấy tài khoản người dùng"));
    }
}
