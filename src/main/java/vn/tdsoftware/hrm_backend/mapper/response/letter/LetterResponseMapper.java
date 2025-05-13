package vn.tdsoftware.hrm_backend.mapper.response.letter;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.letter.response.LetterResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class LetterResponseMapper implements RowMapper<LetterResponse> {

    @Override
    public LetterResponse mapRow(ResultSet resultSet) {
        try {
            return LetterResponse.builder()
                    .letterId(resultSet.getInt("letterId"))
                    .createdBy(resultSet.getString("createdBy"))
                    .employeeName(resultSet.getString("employeeName"))
                    .letterType(resultSet.getInt("letterType"))
                    .letterState(resultSet.getString("letterState"))
                    .createdAt(resultSet.getTimestamp("createdAt"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
