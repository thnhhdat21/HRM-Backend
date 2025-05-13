package vn.tdsoftware.hrm_backend.mapper.response.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.contract.response.WorkProfileResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class WorkProfileResponseMapper implements RowMapper<WorkProfileResponse> {

    @Override
    public WorkProfileResponse mapRow(ResultSet resultSet) {
        try{
            return WorkProfileResponse.builder()
                    .employeeId(resultSet.getLong("employeeId"))
                    .employeeName(resultSet.getString("employeeName"))
                    .employeeCode(resultSet.getString("employeeCode"))
                    .status(resultSet.getInt("status"))
                    .type(resultSet.getInt("type"))
                    .department(resultSet.getString("department"))
                    .jobPosition(resultSet.getString("jobPosition"))
                    .duty(resultSet.getString("duty"))
                    .dateJoin(resultSet.getDate("dateJoin"))
                    .dateSign(resultSet.getDate("dateSign"))
                    .dateOnBoard(resultSet.getDate("dateOnBoard"))
                    .contractName(resultSet.getString("contractName"))
                    .salaryGross(resultSet.getInt("salaryGross"))
                    .account(resultSet.getString("account"))
                    .role(resultSet.getString("role"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
