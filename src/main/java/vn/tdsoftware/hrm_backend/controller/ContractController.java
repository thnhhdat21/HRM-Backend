package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.contract.request.ContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.request.EndContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.response.*;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.service.ContractService;

import java.util.List;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping("/get-work-profile-employee")
    public ResponseData<WorkProfileResponse> getWorkProfile(@RequestParam("employeeId") long employeeId) {
        WorkProfileResponse response = contractService.getWorkProfile(employeeId);
        return ResponseData.<WorkProfileResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/get-work-process-profile")
    public ResponseData<List<WorkProcessResponse>> getContractHistory(@RequestParam("employeeId") long employeeId) {
        List<WorkProcessResponse> response = contractService.getWorkProcess(employeeId);
        return ResponseData.<List<WorkProcessResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/get-contract-profile-by-employee-id")
    public ResponseData<ContractProfileResponse> getContractProfileByEmployee(@RequestParam("employeeId") long employeeId) {
        ContractProfileResponse response = contractService.getContractProfileByEmployee(employeeId);
        return ResponseData.<ContractProfileResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/get-contract-profile-by-contract-id")
    public ResponseData<ContractProfileResponse> getContractProfileByContractIt(@RequestParam("contractId") long contractId) {
        ContractProfileResponse response = contractService.getContractProfileByContractId(contractId);
        return ResponseData.<ContractProfileResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }


    @PostMapping("/get-contract-detail")
    public ResponseData<ContractDetailResponse> getContractDetail(@RequestParam("contractId") long contractId) {
        ContractDetailResponse response = contractService.getContractDetail(contractId);
        return ResponseData.<ContractDetailResponse>builder()
                .code(1000)
                .data(response)
                .message("Get work profile successfully")
                .build();
    }

    @PostMapping("/update-contract")
    public ResponseData<Void> updateContract(@RequestBody ContractRequest contractRequest) {
        contractService.updateContract(contractRequest);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("update contract successfully")
                .build();
    }

    @PostMapping("/create-contract")
    public ResponseData<Void> createContract(@RequestBody ContractRequest contractRequest) {
        contractService.createContract(contractRequest);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("update contract successfully")
                .build();
    }

    @PostMapping("/get-list-contract-of-employee")
    public ResponseData<List<ContractOfEmployeeResponse>> updateContractDix(@RequestParam("employeeId") long employeeId) {
        List<ContractOfEmployeeResponse>  responses = contractService.getListContractOfEmployee(employeeId);
        return ResponseData.<List<ContractOfEmployeeResponse>>builder()
                .code(1000)
                .data(responses)
                .message("update contract successfully")
                .build();
    }

    @PostMapping("/checked-contract")
    public ResponseData<Void> checkedContract(@RequestParam("contractId") long contractId) {
        contractService.checkedContract(contractId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("checked contract successfully")
                .build();
    }

    @PostMapping("/no-checked-contract")
    public ResponseData<Void> noCheckedContract(@RequestParam("contractId") long contractId) {
        contractService.noCheckedContract(contractId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("checked contract successfully")
                .build();
    }

    @PostMapping("/sign-contract")
    public ResponseData<Void> signContract(@RequestParam("contractId") long contractId) {
        contractService.signContract(contractId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("checked contract successfully")
                .build();
    }

    @PostMapping("/get-list-contract")
    public ResponseData<List<ContractResponse>> getListContract(@RequestBody EmployeeFilter filter) {
        List<ContractResponse> responses = contractService.getListContract(filter);
        return ResponseData.<List<ContractResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list contract successfully")
                .build();
    }

    @PostMapping("/end-contract")
    public ResponseData<Void> endContract(@RequestBody EndContractRequest request) {
        contractService.endContract(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("end contract successfully")
                .build();
    }

    @PostMapping("/count-contract-appendix")
    public ResponseData<Integer> endContract(@RequestParam long contractId) {
        int count =  contractService.countContractAppendix(contractId);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(count)
                .message("end contract successfully")
                .build();
    }
}
