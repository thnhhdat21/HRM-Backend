package vn.tdsoftware.hrm_backend.controller.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.account.request.ActiveAccountRequest;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.service.AccountService;

import java.util.List;

@RestController()
@RequestMapping("/admin/account")
@RequiredArgsConstructor
public class ManageAccountController {
    private final AccountService accountService;

    @PostMapping("/get-list-account")
    private ResponseData<List<AccountResponse>> getListAccount(@RequestBody EmployeeFilter filter) {
        List<AccountResponse> response = accountService.getListAccount(filter);
        return ResponseData.<List<AccountResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get list account successfully")
                .build();
    }

    @PostMapping("/get-count-account-type")
    private ResponseData<AccountTypeResponse> getCountAccountType(@RequestBody EmployeeFilter filter) {
        AccountTypeResponse response = accountService.getAccountCountType(filter);
        return ResponseData.<AccountTypeResponse>builder()
                .code(1000)
                .data(response)
                .message("Get count account successfully")
                .build();
    }

    @PostMapping("/get-count-account")
    private ResponseData<Integer> getCountAccount(@RequestBody EmployeeFilter filter) {
        int response = accountService.getAccountCount(filter);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(response)
                .message("Get count account successfully")
                .build();
    }

    @PostMapping("/lock-account")
    private ResponseData<Void> lockAccount(@RequestParam("id") long id) {
        accountService.lockAccount(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Lock account successfully")
                .build();
    }

    @PostMapping("/unlock-account")
    private ResponseData<Void> unlockAccount(@RequestParam("id") long id) {
        accountService.unlockAccount(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("UnLock account successfully")
                .build();
    }

    @PostMapping("/change-password")
    private ResponseData<Void> changePassword(@RequestParam("id") long id, @RequestParam("password") String password) {
        accountService.changePassword(id, password);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("change password successfully")
                .build();
    }

    @PostMapping("/active-account")
    private ResponseData<Void> activeAccount(@RequestBody ActiveAccountRequest request) {
        String fullName =  accountService.activeAccount(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Kích hoạt tài khoản thành công cho nhân sự: " + fullName)
                .build();
    }

    @PostMapping("/delete-account")
    private ResponseData<Void> deleteAccount(@RequestParam("id") long id) {
        accountService.deleteId(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Xóa tài khoản thành công")
                .build();
    }

}
