package vn.tdsoftware.hrm_backend.mapper.response.salary;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.TaxDTO;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.time.YearMonth;

public class TaxDTOMapper implements RowMapper<TaxDTO> {

    @Override
    public TaxDTO mapRow(ResultSet resultSet) {
        try {
                return   TaxDTO.builder()
                        .employeeId(resultSet.getLong("employeeId"))
                        .employeeName(resultSet.getString("employeeName"))
                        .employeeCode(resultSet.getString("employeeCode"))
                        .department(resultSet.getString("department"))
                        .jobPosition(resultSet.getString("jobPosition"))
                        .yearMonth(resultSet.getString("yearMonth") != null? YearMonth.parse(resultSet.getString("yearMonth")) : null)
                        .salary(resultSet.getInt("salaryReal"))
                        .tax(resultSet.getInt("totalTax"))
                        .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
