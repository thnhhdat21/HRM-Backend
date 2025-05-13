package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.contract.response.*;
import vn.tdsoftware.hrm_backend.service.ContractService;

import java.util.List;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping("/get-work-profile-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_CONTRACT_COMPANY', " +
                                    "'ROLE_WATCH_CONTRACT_DEPARTMENT', " +
                                    "'ROLE_WATCH_SELF_CONTRACT')")
    public ResponseData<WorkProfileResponse> getWorkProfile(@RequestParam("employeeId") long employeeId) {
        WorkProfileResponse response = contractService.getWorkProfile(employeeId);
        return ResponseData.<WorkProfileResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/get-work-process-profile")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_CONTRACT_COMPANY', " +
                                    "'ROLE_WATCH_CONTRACT_DEPARTMENT', " +
                                    "'ROLE_WATCH_SELF_CONTRACT')")
    public ResponseData<List<WorkProcessResponse>> getContractHistory(@RequestParam("employeeId") long employeeId) {
        List<WorkProcessResponse> response = contractService.getWorkProcess(employeeId);
        return ResponseData.<List<WorkProcessResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/get-contract-profile-by-employee-id")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_CONTRACT_COMPANY', " +
                                    "'ROLE_WATCH_CONTRACT_DEPARTMENT', " +
                                    "'ROLE_WATCH_SELF_CONTRACT')")
    public ResponseData<ContractProfileResponse> getContractProfileByEmployee(@RequestParam("employeeId") long employeeId) {
        ContractProfileResponse response = contractService.getContractProfileByEmployee(employeeId);
        return ResponseData.<ContractProfileResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/get-contract-profile-by-contract-id")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_CONTRACT_COMPANY', " +
                                    "'ROLE_WATCH_CONTRACT_DEPARTMENT', " +
                                    "'ROLE_WATCH_SELF_CONTRACT')")
    public ResponseData<ContractProfileResponse> getContractProfileByContractId(@RequestParam("contractId") long contractId) {
        ContractProfileResponse response = contractService.getContractProfileByContractId(contractId);
        return ResponseData.<ContractProfileResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/get-list-contract-of-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_CONTRACT_COMPANY', " +
                                    "'ROLE_WATCH_CONTRACT_DEPARTMENT', " +
                                    "'ROLE_WATCH_SELF_CONTRACT')")
    public ResponseData<List<ContractOfEmployeeResponse>> getListContractOfEmployee(@RequestParam("employeeId") long employeeId) {
        List<ContractOfEmployeeResponse>  responses = contractService.getListContractOfEmployee(employeeId);
        return ResponseData.<List<ContractOfEmployeeResponse>>builder()
                .code(1000)
                .data(responses)
                .message("update contract successfully")
                .build();
    }
}
