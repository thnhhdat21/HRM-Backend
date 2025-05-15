package vn.tdsoftware.hrm_backend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeResponse;
import vn.tdsoftware.hrm_backend.service.ContractTypeService;

import java.util.List;

@RestController
@RequestMapping("/contract-type")
@RequiredArgsConstructor
public class ContractTypeController {
    private final ContractTypeService contractTypeService;

    @PostMapping("/get-list")
    public ResponseData<List<ContractTypeResponse>> getList() {
        List<ContractTypeResponse> responses = contractTypeService.getListContractType();
        return ResponseData.<List<ContractTypeResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get List successfully")
                .build();
    }
}
