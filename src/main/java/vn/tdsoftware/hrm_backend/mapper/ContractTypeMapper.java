package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.contracttype.request.ContractTypeRequest;
import vn.tdsoftware.hrm_backend.dto.contracttype.request.ContractTypeUpdate;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeResponse;
import vn.tdsoftware.hrm_backend.entity.ContractType;

public class ContractTypeMapper {
    public static ContractTypeResponse mapToTypeContractResponse(ContractType typeContract) {
        return ContractTypeResponse.builder()
                .id(typeContract.getId())
                .name(typeContract.getName())
                .type(typeContract.getType())
                .term(typeContract.getTerm())
                .unit(typeContract.getUnit())
                .insurance(typeContract.getInsurance())
                .status(typeContract.getStatus())
                .build();

    }

    public static ContractTypeRequest mapToTypeContractRequest(ContractTypeUpdate contractType) {
        return ContractTypeRequest.builder()
                .id(contractType.getId())
                .name(contractType.getName())
                .type(contractType.getType())
                .insurance(contractType.getInsurance())
                .method(contractType.getMethod())
                .term(contractType.getTerm())
                .unit(contractType.getUnit())
                .allowances(contractType.getAllowances())
                .build();
    }
}
