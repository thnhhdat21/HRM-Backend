package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.ApprovalReason;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalReasonRepository extends JpaRepository<ApprovalReason, Long> {
    Optional<List<ApprovalReason>> findApprovalReasonByApprovalTypeIdAndIsEnabled(int approvalTypeId, boolean isEnabled);
    Optional<ApprovalReason> findApprovalReasonByIdAndIsEnabled(long id, boolean isEnabled);
}
