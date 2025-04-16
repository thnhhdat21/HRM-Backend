package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.InsuranceRatioDAO;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioDetailResponse;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioResponse;
import vn.tdsoftware.hrm_backend.entity.InsuranceRatio;
import vn.tdsoftware.hrm_backend.mapper.response.insurance.InsuranceRatioDetailResponseMapper;

import java.util.List;

@Component
public class InsuranceRatioDAOImpl extends AbstractDao<InsuranceRatio> implements InsuranceRatioDAO {

    @Override
    public InsuranceRatioDetailResponse getAllInsuranceRatioById(int id) {
        String sql = "select insuranceRatio.id, " +
                "insuranceRatio.dateStart, " +
                "insuranceRatioDetail.id as detailId, " +
                "insuranceRatioDetail.insuranceType as type, " +
                "insuranceRatioDetail.ratio , " +
                "insuranceRatioDetail.companyPay  " +
                "from insuranceRatioDetail  " +
                "right join insuranceRatio on insuranceRatioDetail.insuranceRatioId = insuranceRatio.id and insuranceRatioDetail.isEnabled = true " +
                "where insuranceRatio.isEnabled = true and insuranceRatio.id = ?";
        List<InsuranceRatioDetailResponse> list = queryDetails(sql, new InsuranceRatioDetailResponseMapper(), id);
        return list.get(0);
    }

    @Override
    public void deleteInsuranceRatio(String listId) {
        String sql = "UPDATE insuranceRatio " +
                "SET isEnabled = 0 " +
                "WHERE id IN ( " + listId + " )";
        update(sql);
    }

    @Override
    public void deleteInsuranceRatioDetail(String listId) {
        String sql = "UPDATE insuranceRatioDetail " +
                "SET isEnabled = 0 " +
                "WHERE insuranceRatioDetail.insuranceRatioId IN ( " + listId + " )";
        update(sql);
    }
}
