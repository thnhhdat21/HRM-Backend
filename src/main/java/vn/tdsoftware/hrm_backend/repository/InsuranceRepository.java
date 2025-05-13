package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Insurance;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Optional<Insurance> findInsuranceByEmployeeIdAndYearMonthAndIsEnabled(Long id, String yearMonth, Boolean isEnabled);
    Optional<List<Insurance>> findAllByYearMonthAndStatusAndIsEnabled(String yearMonth, int status, Boolean isEnabled);
    Optional<List<Insurance>> findAllByYearMonthAndStatusNotAndIsEnabled(String yearMonth, int status, Boolean isEnabled);

}
