package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.common.service.EmailService;
import vn.tdsoftware.hrm_backend.common.thread.SendMailThread;
import vn.tdsoftware.hrm_backend.dao.AccountDAO;
import vn.tdsoftware.hrm_backend.dto.account.request.ActiveAccountRequest;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountCountResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountDetailResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;
import vn.tdsoftware.hrm_backend.entity.*;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.AccountRepository;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;
import vn.tdsoftware.hrm_backend.service.AccountService;
import vn.tdsoftware.hrm_backend.util.AccountUtil;
import vn.tdsoftware.hrm_backend.util.FieldStringUtil;
import vn.tdsoftware.hrm_backend.util.constant.AccountConstant;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;
    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AccountResponse> getListAccount(int type) {
        if (type > 3 || type < 1) {
            throw new BusinessException(ErrorCode.TYPE_ACCOUNT_NOT_EXIST);
        }
        List<AccountResponse> accountResponses = accountDAO.getListAccount(type);
        if (accountResponses.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_ACCOUNT_IS_EMPTY);
        }
        return accountResponses;
    }

    @Override
    public AccountCountResponse getAccountCount() {
        return accountDAO.getAccountCount();
    }

    @Override
    public void unlockAccount(long id) {
        Account account = checkAccount(id);
        if (account.getStatus() == 1) {
            throw new BusinessException(ErrorCode.ACCOUNT_UNLOCKED);
        }
        account.setStatus(1);
        accountRepository.save(account);
    }

    @Override
    public void changePassword(long id, String password) {
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PASSWORD_INVALID);
        }
        Account account = checkAccount(id);
        Employee employee = employeeRepository.findByIdAndIsEnabled(account.getEmployeeId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
        );
        account.setPassword(passwordEncoder.encode(password));

        Thread sendEmailThread = new Thread(new SendMailThread(emailService, employee.getEmail(), account.getUsername(), account.getPassword()));
        sendEmailThread.start();
        accountRepository.save(account);
    }

    @Override
    public void lockAccount(long id) {
        Account account = checkAccount(id);
        if (account.getStatus() == 3) {
            throw new BusinessException(ErrorCode.ACCOUNT_LOCKED);
        }
        account.setStatus(3);
        accountRepository.save(account);
    }


    @Override
    @Transactional
    public String activeAccount(ActiveAccountRequest request) {
        Employee employee = checkEmployee(request.getEmployeeCode());
        Account account = accountRepository.findByEmployeeIdAndIsEnabled(employee.getId(), true);
        if (account == null) {
            throw new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY);
        }
        if (account.getStatus() != AccountConstant.ACCOUNT_NOT_ACTIVE) {
            throw new BusinessException(ErrorCode.ACCOUNT_ACTIVATED);
        }
        FieldStringUtil.validatorPass(request.getPassword());

        Thread sendEmailThread = new Thread(new SendMailThread(emailService, employee.getEmail(), account.getUsername(), account.getPassword()));
        sendEmailThread.start();
        account.setStatus(AccountConstant.ACCOUNT_ACTIVE);
        account.setActiveAt(new Date());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(account);
        return employee.getFullName();
    }

    @Override
    public void deleteId(long id) {
        checkAccount(id);
        accountDAO.deleteAccount(id);
    }

    @Override
    public AccountDetailResponse getDetailAccount(long id) {
        checkAccount(id);
        AccountDetailResponse response = accountDAO.getAccountDetail(id);
        if (response == null) {
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_EXIST);
        }
        return response;
    }

    @Override
    public void createAccount(long employeeId, String employeeName) {
        accountRepository.save(Account.builder()
                        .employeeId(employeeId)
                        .username(AccountUtil.generateUsername(employeeName, employeeId))
                        .status(AccountConstant.ACCOUNT_NOT_ACTIVE)
                .build());
    }

    private Employee checkEmployee(String employeeCode) {
        if (!employeeCode.startsWith("TDS") || employeeCode.length() < 6) {
            throw new BusinessException(ErrorCode.EMPLOYEE_CODE_NOT_EXIST);
        }
        Employee employee = employeeRepository.findByEmployeeCodeAndIsEnabled(employeeCode, true);
        if (employee == null) {
            throw new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY);
        }
        return employee;
    }

    private Account checkAccount(long id) {
        if (id < 0) {
            throw new BusinessException(ErrorCode.ID_NOT_EXIST);
        }
        Account account = accountRepository.findByIdAndIsEnabled(id, true);
        if (account == null) {
            throw new BusinessException(ErrorCode.LIST_ACCOUNT_IS_EMPTY);
        }
        return account;
    }
}
