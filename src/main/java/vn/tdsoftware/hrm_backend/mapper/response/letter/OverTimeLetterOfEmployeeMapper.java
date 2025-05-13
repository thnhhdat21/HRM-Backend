package vn.tdsoftware.hrm_backend.mapper.response.letter;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.letter.response.OverTimeLetterOfEmployee;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class OverTimeLetterOfEmployeeMapper implements RowMapper<OverTimeLetterOfEmployee> {

    @Override
    public OverTimeLetterOfEmployee mapRow(ResultSet resultSet) {
        try {
            return OverTimeLetterOfEmployee.builder()
                    .total(resultSet.getInt("total"))
                    .dateRegis(resultSet.getDate("dateRegis").toLocalDate())
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
