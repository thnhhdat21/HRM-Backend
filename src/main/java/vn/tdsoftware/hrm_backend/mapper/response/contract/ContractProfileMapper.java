package vn.tdsoftware.hrm_backend.mapper.response.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractProfileResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContractProfileMapper implements RowMapper<ContractProfileResponse> {

    @Override
    public ContractProfileResponse mapRow(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                ContractProfileResponse contractDetailResponse = ContractProfileResponse.builder()
                        .contractId(resultSet.getLong("contractId"))
                        .parent(resultSet.getLong("parent"))
                        .contractCode(resultSet.getString("contractCode"))
                        .employeeName(resultSet.getString("employeeName"))
                        .employeeCode(resultSet.getString("employeeCode"))
                        .contractType(resultSet.getString("contractType"))
                        .state(resultSet.getInt("contractState"))
                        .status(resultSet.getInt("contractStatus"))
                        .department(resultSet.getString("department"))
                        .jobPosition(resultSet.getString("jobPosition"))
                        .duty(resultSet.getString("duty"))
                        .method(resultSet.getString("method"))
                        .dateSign(resultSet.getDate("dateSign"))
                        .dateStart(resultSet.getDate("dateStart"))
                        .dateEnd(resultSet.getDate("dateEnd"))
                        .contractStatus(resultSet.getInt("contractStatus"))
                        .salaryGross(resultSet.getInt("salaryGross"))
                        .description(resultSet.getString("description"))
                        .build();
                List<AllowanceResponse> allowances = new ArrayList<>();
                do {
                    allowances.add(AllowanceResponse.builder()
                                    .name(resultSet.getString("allowanceName"))
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
