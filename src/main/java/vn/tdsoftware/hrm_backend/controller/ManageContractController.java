package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.contract.request.ContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.request.EndContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractDetailResponse;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.service.ContractService;

import java.util.List;

@RestController
@RequestMapping("/manage-contract")
@RequiredArgsConstructor
public class ManageContractController {
    private final ContractService contractService;

    @PostMapping("/get-contract-detail")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_CONTRACT')")
    public ResponseData<ContractDetailResponse> getContractDetail(@RequestParam("contractId") long contractId) {
        ContractDetailResponse response = contractService.getContractDetail(contractId);
        return ResponseData.<ContractDetailResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/update-contract")
    @PreAuthorize("hasAuthority('ROLE_MANAGE_CONTRACT')")
    public ResponseData<Void> updateContract(@RequestBody ContractRequest contractRequest) {
        contractService.updateContract(contractRequest);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("update contract successfully")
                .build();
    }

    @PostMapping("/create-contract")
    @PreAuthorize("hasAuthority('ROLE_CREATE_CONTRACT')")
    public ResponseData<Void> createContract(@RequestBody ContractRequest contractRequest) {
        contractService.createContract(contractRequest);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("update contract successfully")
                .build();
    }

    @PostMapping("/get-list-contract")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_CONTRACT_COMPANY', 'ROLE_WATCH_CONTRACT_DEPARTMENT')")
    public ResponseData<List<ContractResponse>> getListContract(@RequestBody EmployeeFilter filter) {
        List<ContractResponse> responses = contractService.getListContract(filter);
        return ResponseData.<List<ContractResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list contract successfully")
                .build();
    }

    @PostMapping("/end-contract")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_CONTRACT')")
    public ResponseData<Void> endContract(@RequestBody EndContractRequest request) {
        contractService.endContract(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("end contract successfully")
                .build();
    }

    @PostMapping("/count-contract-appendix")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_CONTRACT_COMPANY', 'ROLE_WATCH_CONTRACT_DEPARTMENT')")
    public ResponseData<Integer> countContractAppendix(@RequestParam long contractId) {
        int count = contractService.countContractAppendix(contractId);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(count)
                .message("end contract successfully")
                .build();
    }

}
