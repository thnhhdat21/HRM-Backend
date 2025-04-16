package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioDetailResponse;


@Repository
public interface InsuranceRatioDAO {
    InsuranceRatioDetailResponse getAllInsuranceRatioById(int id);
    void deleteInsuranceRatio(String listId);
    void deleteInsuranceRatioDetail(String listId);
}
