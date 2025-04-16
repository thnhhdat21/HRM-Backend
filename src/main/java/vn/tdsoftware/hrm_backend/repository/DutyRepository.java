package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Duty;

import java.util.List;

@Repository
public interface DutyRepository extends JpaRepository<Duty,Long> {
    List<Duty> findByIsEnabled(boolean isEnabled);
    Duty findByIdAndIsEnabled(Long id, boolean isEnabled);
}
