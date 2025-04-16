package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.LeaveSetting;

import java.util.Optional;

@Repository
public interface LeaveSettingRepository extends JpaRepository<LeaveSetting, Integer> {
    Optional<LeaveSetting> findLeaveSettingByIsEnabled(boolean isEnabled);
    Optional<LeaveSetting> findLeaveSettingByIdAndIsEnabled(long id, boolean isEnabled);
}
