package vn.tdsoftware.hrm_backend.mapper.response.letter;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.letter.response.CountLetterResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class CountLetterMapper implements RowMapper<CountLetterResponse> {
    @Override
    public CountLetterResponse mapRow(ResultSet resultSet) {
        try {
            return CountLetterResponse.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .count(resultSet.getInt("count"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
