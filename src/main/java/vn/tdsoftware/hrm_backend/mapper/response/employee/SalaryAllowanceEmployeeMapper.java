package vn.tdsoftware.hrm_backend.mapper.response.employee;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.dto.employee.response.SalaryAllowanceEmployee;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SalaryAllowanceEmployeeMapper implements RowMapper<SalaryAllowanceEmployee> {
    @Override
    public SalaryAllowanceEmployee mapRow(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                SalaryAllowanceEmployee response = SalaryAllowanceEmployee.builder()
                        .employeeId(resultSet.getLong("employeeId"))
                        .salaryGross(resultSet.getInt("salaryGross"))
                        .allowances(new ArrayList<>())
                        .build();

                do {
                    response.getAllowances().add(AllowanceResponse.builder()
                                    .name(resultSet.getString("name"))
                                    .amount(resultSet.getInt("amount"))
                                    .unit(resultSet.getString("unit"))
                            .build());
                } while (resultSet.next());
                return response;
            }
            return null;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
