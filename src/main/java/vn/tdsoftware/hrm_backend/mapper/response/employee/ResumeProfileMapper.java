package vn.tdsoftware.hrm_backend.mapper.response;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.employee.response.ResumeProfileResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class ResumeProfileMapper implements RowMapper<ResumeProfileResponse> {
    @Override
    public ResumeProfileResponse mapRow(ResultSet resultSet) {
        try {
            return ResumeProfileResponse.builder()
                    .id(resultSet.getLong("id"))
                    .fullName(resultSet.getString("fullName"))
                    .employeeCode(resultSet.getString("employeeCode"))
                    .dateOfBirth(resultSet.getDate("dateOfBirth"))
                    .gender(resultSet.getString("gender"))
                    .marriageStatus(resultSet.getString("marriageStatus"))
                    .nation(resultSet.getString("nation"))
                    .phoneNumber(resultSet.getString("phoneNumber"))
                    .email(resultSet.getString("email"))
                    .religion(resultSet.getString("religion"))
                    .ethnic(resultSet.getString("ethnic"))
                    .identityCard(resultSet.getString("identityCard"))
                    .issueDateCCCD(resultSet.getDate("issueDateCCCD"))
                    .placeCCCD(resultSet.getString("placeCCCD"))
                    .placeOfBirth(resultSet.getString("placeOfBirth"))
                    .homeTown(resultSet.getString("homeTown"))
                    .permanentAddress(resultSet.getString("permanentAddress"))
                    .currentAddress(resultSet.getString("currentAddress"))
                    .accountBank(resultSet.getString("accountNumber"))
                    .fontImageCCCD(resultSet.getString("fontImageCCCD"))
                    .backImageCCCD(resultSet.getString("backImageCCCD"))
                    .taxCode(resultSet.getString("taxCode"))
                    .bankName(resultSet.getString("bankName"))
                    .bankAccountName(resultSet.getString("bankAccountName"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
