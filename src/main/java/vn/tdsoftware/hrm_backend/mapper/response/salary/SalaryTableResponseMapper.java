package vn.tdsoftware.hrm_backend.mapper.response.salary;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryTableResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class SalaryTableResponseMapper implements RowMapper<SalaryTableResponse> {
    @Override
    public SalaryTableResponse mapRow(ResultSet resultSet) {
        try {
            return SalaryTableResponse.builder()
                    .yearMonth(resultSet.getString("yearMonth"))
                    .id(resultSet.getLong("id"))
                    .nameSalaryTable(resultSet.getString("name"))
                    .status(resultSet.getInt("status"))
                    .numberEmployee(resultSet.getInt("numberEmployee"))
                    .totalAmount(resultSet.getLong("totalAmount"))
                    .createdAt(resultSet.getDate("createdAt"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
