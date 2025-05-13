package vn.tdsoftware.hrm_backend.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.contracttype.request.ContractTypeRequest;
import vn.tdsoftware.hrm_backend.dto.contracttype.request.ContractTypeUpdate;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeDetail;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.service.ContractTypeService;

import java.util.List;

@RestController
@RequestMapping("/admin/contract-type")
@RequiredArgsConstructor
public class ManageContractTypeController {
    private final ContractTypeService contractTypeService;
    private final Gson gson;

    @PostMapping("/get-list")
    public ResponseData<List<ContractTypeResponse>> getList() {
        List<ContractTypeResponse> responses = contractTypeService.getListContractType();
        return ResponseData.<List<ContractTypeResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get List successfully")
                .build();
    }

    @PostMapping("/create-contract-type")
    public ResponseData<ContractTypeResponse> getList(@RequestBody ContractTypeRequest request) {
        ContractTypeResponse response = contractTypeService.createContractType(request);
        return ResponseData.<ContractTypeResponse>builder()
                .code(1000)
                .data(response)
                .message("Get List successfully")
                .build();
    }

    @PostMapping("/get-contract-type")
    public ResponseData<ContractTypeDetail> getList(@RequestParam("id") long id) {
        ContractTypeDetail response = contractTypeService.getContractType(id);
        return ResponseData.<ContractTypeDetail>builder()
                .code(1000)
                .data(response)
                .message("Get contract type successfully")
                .build();
    }

    @PostMapping("/update-contract-type")
    public ResponseData<ContractTypeResponse> updateContractType(@RequestBody ContractTypeUpdate request) {
        ContractTypeResponse response = contractTypeService.updateContractType(request);
        return ResponseData.<ContractTypeResponse>builder()
                .code(1000)
                .data(response)
                .message("Get contract type successfully")
                .build();
    }

    @PostMapping("/get-count-contract-type")
    public ResponseData<List<CountContractTypeResponse>> getCountContractType(@RequestBody EmployeeFilter filter) {
        List<CountContractTypeResponse> response = contractTypeService.getCountContractType(filter);
        return ResponseData.<List<CountContractTypeResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get contract type successfully")
                .build();
    }

    @PostMapping("/delete-contract-type")
    public ResponseData<Void> delete(@RequestParam long id) {
        contractTypeService.deleteContractType(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get contract type successfully")
                .build();
    }
}
