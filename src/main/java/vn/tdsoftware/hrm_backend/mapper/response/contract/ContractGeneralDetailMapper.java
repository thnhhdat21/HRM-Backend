package vn.tdsoftware.hrm_backend.mapper.response.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractDetailResponse;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractGeneralDetail;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractHasAllowanceResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContractGeneralDetailMapper implements RowMapper<ContractGeneralDetail> {

    @Override
    public ContractGeneralDetail mapRow(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                ContractGeneralDetail contractDetailResponse = ContractGeneralDetail.builder()
                        .salaryGross(resultSet.getInt("salaryGross"))
                        .contractId(resultSet.getLong("contractId"))
                        .department(resultSet.getLong("department"))
                        .jobPosition(resultSet.getLong("jobPosition"))
                        .isAutoCheckin(resultSet.getBoolean("isAutoCheckin"))
                        .build();
                List<ContractHasAllowanceResponse> allowances = new ArrayList<>();
                do {
                    allowances.add(ContractHasAllowanceResponse.builder()
                            .id(resultSet.getLong("contractHasAllowanceId"))
                            .allowanceId(resultSet.getLong("allowanceId"))
                            .amount(resultSet.getInt("allowanceAmount"))
                            .unit(resultSet.getString("allowanceUnit"))
                            .build());
                } while (resultSet.next());
                contractDetailResponse.setAllowances(allowances);
                return contractDetailResponse;
            }
            return null;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
