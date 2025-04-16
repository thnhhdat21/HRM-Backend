package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tdsoftware.hrm_backend.entity.InsuranceSetting;

import java.util.Optional;

public interface InsuranceSettingRepository extends JpaRepository<InsuranceSetting, Integer> {
    Optional<InsuranceSetting> findInsuranceSettingByIsEnabled(boolean isEnabled);
    Optional<InsuranceSetting> findInsuranceSettingByIdAndIsEnabled(long id, boolean isEnabled);

}
