package vn.tdsoftware.hrm_backend.mapper.response.insurance;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioDetailResponse;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.RatioDetail;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.*;

public class InsuranceRatioDetailResponseMapper implements RowMapper<InsuranceRatioDetailResponse> {

    @Override
    public InsuranceRatioDetailResponse mapRow(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                InsuranceRatioDetailResponse response = InsuranceRatioDetailResponse.builder()
                        .id(resultSet.getInt("id"))
                        .dateStart(resultSet.getDate("dateStart"))
                        .build();
                List<RatioDetail> listRatios = new ArrayList<>();
                 do {
                   RatioDetail ratioDetail = RatioDetail.builder()
                           .detailId(resultSet.getInt("detailId"))
                           .type(resultSet.getInt("type"))
                           .ratio(resultSet.getDouble("ratio"))
                           .companyPay(resultSet.getDouble("companyPay"))
                           .build();
                   listRatios.add(ratioDetail);
                }while (resultSet.next());
                response.setRatios(listRatios);
                return response;
            }
            return  null;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
