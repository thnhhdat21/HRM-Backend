package vn.tdsoftware.hrm_backend.mapper.response.salary;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryTableResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class SalaryDetailResponseMapper implements RowMapper<SalaryDetailResponse> {

    @Override
    public SalaryDetailResponse mapRow(ResultSet resultSet) {
        try {
            return SalaryDetailResponse.builder()
                    .employeeId(resultSet.getLong("employeeId"))
                    .employeeCode(resultSet.getString("employeeCode"))
                    .employeeName(resultSet.getString("employeeName"))
                    .department(resultSet.getString("department"))
                    .jobPosition(resultSet.getString("jobPosition"))
                    .workDayReal(resultSet.getInt("workDayReal"))
                    .totalWorkDay(resultSet.getInt("totalWorkDay"))
                    .salaryGross(resultSet.getInt("salaryGross"))
                    .salaryWorkDay(resultSet.getInt("salaryWorkDay"))
                    .totalAllowance(resultSet.getInt("totalAllowance"))
                    .reward(resultSet.getInt("reward"))
                    .penalty(resultSet.getInt("penalty"))
                    .salaryOTOnWeek(resultSet.getInt("salaryOTOnWeek"))
                    .salaryOTLastWeek(resultSet.getInt("salaryOTLastWeek"))
                    .salaryOTHoliday(resultSet.getInt("salaryOTHoliday"))
                    .totalInsurance(resultSet.getInt("totalInsurance"))
                    .totalTax(resultSet.getInt("totalTax"))
                    .salaryReal(resultSet.getInt("salaryReal"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
