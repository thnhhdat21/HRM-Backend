package vn.tdsoftware.hrm_backend.service;


import vn.tdsoftware.hrm_backend.dto.approvalreason.request.ApprovalReasonRequest;
import vn.tdsoftware.hrm_backend.dto.approvalreason.response.ApprovalReasonDetail;
import vn.tdsoftware.hrm_backend.dto.approvalreason.response.ApprovalReasonResponse;

import java.util.List;

public interface ApprovalReasonService {
    List<ApprovalReasonResponse> getApprovalReason(int type);
    List<ApprovalReasonResponse> createApprovalReason(List<ApprovalReasonRequest> request);
    ApprovalReasonResponse updateApprovalReason(ApprovalReasonRequest request);
    void deleteApprovalReason(Long id);
    ApprovalReasonDetail getApprovalReasonDetail(Long id);
}
