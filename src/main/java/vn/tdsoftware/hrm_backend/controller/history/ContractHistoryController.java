package vn.tdsoftware.hrm_backend.controller.history;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.history.contract.response.ContractHistoryResponse;
import vn.tdsoftware.hrm_backend.service.history.ContractHistoryService;

import java.util.List;

@RestController
@RequestMapping("/contract-history")
@RequiredArgsConstructor
public class ContractHistoryController {
    private final ContractHistoryService contractHistoryService;

    @PostMapping("/get-contract-history")
    public ResponseData<List<ContractHistoryResponse>> getContractHistory(@RequestParam("contractId") long contractId) {
        List<ContractHistoryResponse> response = contractHistoryService.getContractHistory(contractId);
        return ResponseData.<List<ContractHistoryResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get contract history success")
                .build();
    }
}
