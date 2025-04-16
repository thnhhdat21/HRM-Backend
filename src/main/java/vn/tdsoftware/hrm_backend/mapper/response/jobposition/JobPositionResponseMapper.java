package vn.tdsoftware.hrm_backend.mapper.response.jobposition;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class JobPositionResponseMapper implements RowMapper<JobPositionResponse> {

    @Override
    public JobPositionResponse mapRow(ResultSet resultSet) {
        try {
            return JobPositionResponse.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .role(resultSet.getString("role"))
                    .salaryFrom(resultSet.getInt("salaryFrom"))
                    .salaryTo(resultSet.getInt("salaryTo"))
                    .status(resultSet.getString("status"))
                    .createBy(resultSet.getString("createBy"))
                    .des(resultSet.getString("des"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
