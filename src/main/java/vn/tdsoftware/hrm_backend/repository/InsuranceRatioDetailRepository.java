package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.InsuranceRatioDetail;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRatioDetailRepository extends JpaRepository<InsuranceRatioDetail, Long> {
    Optional<InsuranceRatioDetail> findByIdAndIsEnabled(long id, boolean isEnabled);
    List<InsuranceRatioDetail> findByInsuranceRatioIdAndIsEnabled(int id, boolean isEnabled);
}
