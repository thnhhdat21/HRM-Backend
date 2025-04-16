package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.approvalreason.response.ApprovalReasonDetail;
import vn.tdsoftware.hrm_backend.dto.approvalreason.response.ApprovalReasonResponse;
import vn.tdsoftware.hrm_backend.entity.ApprovalReason;

public class ApprovalReasonMapper {

    public static ApprovalReasonResponse mapToApprovalReasonResponse(ApprovalReason approvalReason) {
        return ApprovalReasonResponse.builder()
                .id(approvalReason.getId())
                .reason(approvalReason.getReason())
                .maximum(approvalReason.getMaximum())
                .workDayEnabled(approvalReason.getWorkDayEnabled())
                .createdAt(approvalReason.getCreatedAt())
                .des(approvalReason.getDescription())
                .status(approvalReason.getStatus())
                .build();
    }

    public static ApprovalReasonDetail mapToApprovalReasonDetail(ApprovalReason approvalReason) {
        return ApprovalReasonDetail.builder()
                .id(approvalReason.getId())
                .reason(approvalReason.getReason())
                .symbol(approvalReason.getReason())
                .maximum(approvalReason.getMaximum())
                .unit(approvalReason.getUnit())
                .workDayEnabled(approvalReason.getWorkDayEnabled())
                .goLate(approvalReason.getGoLate())
                .backEarly(approvalReason.getBackEarly())
                .des(approvalReason.getDescription())
                .build();
    }
}
