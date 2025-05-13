package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.allowance.request.AllowanceRequest;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.service.AllowanceService;

import java.util.List;

@RestController
@RequestMapping("/allowance")
@RequiredArgsConstructor
public class AllowanceController {
    private final AllowanceService allowanceService;

    @PostMapping("/get-allowance-by-contract-type")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_CONTRACT', 'ROLE_CREATE_CONTRACT')")
    public ResponseData<List<AllowanceResponse>> getAllowanceByContractType(@RequestParam("contractTypeId") long contractTypeId) {
        List<AllowanceResponse>  responses = allowanceService.getAllowanceByContractType(contractTypeId);
        return ResponseData.<List<AllowanceResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Delete successfully")
                .build();
    }
}
