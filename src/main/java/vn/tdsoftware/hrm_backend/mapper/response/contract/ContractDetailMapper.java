package vn.tdsoftware.hrm_backend.mapper.response.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractDetailResponse;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractHasAllowanceResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContractDetailMapper implements RowMapper<ContractDetailResponse> {
    @Override
    public ContractDetailResponse mapRow(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                ContractDetailResponse contractDetailResponse = ContractDetailResponse.builder()
                        .contractId(resultSet.getLong("contractId"))
                        .employeeId(resultSet.getLong("employeeId"))
                        .contractCode(resultSet.getString("contractCode"))
                        .employeeName(resultSet.getString("employeeName"))
                        .employeeCode(resultSet.getString("employeeCode"))
                        .contractType(resultSet.getLong("contractType"))
                        .department(resultSet.getLong("department"))
                        .jobPosition(resultSet.getLong("jobPosition"))
                        .duty(resultSet.getLong("duty"))
                        .contractMethod(resultSet.getString("method"))
                        .dateSign(resultSet.getDate("dateSign"))
                        .dateStart(resultSet.getDate("dateStart"))
                        .dateEnd(resultSet.getDate("dateEnd"))
                        .salaryGross(resultSet.getInt("salaryGross"))
                        .parent(resultSet.getLong("parent"))
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
