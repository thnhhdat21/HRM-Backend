package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.decision.response.CountDecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.DecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.rewardandpenalty.request.RewardAndPenaltyDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.salary.request.SalaryDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.termination.resquest.TerminationDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.transferandappoint.request.TransferDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.service.DecisionService;

import java.util.List;

@RestController
@RequestMapping("/decision")
@RequiredArgsConstructor
public class DecisionController {
    private final DecisionService decisionService;

    @PostMapping("/get-list-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_DECISION_COMPANY', 'ROLE_WATCH_DECISION_DEPARTMENT')")
    public ResponseData<List<DecisionResponse>> getListDecision(@RequestBody EmployeeFilter filter) {
        List<DecisionResponse> response = decisionService.getListDecision(filter);
        return ResponseData.<List<DecisionResponse>>builder()
                .code(1000)
                .data(response)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/count-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_DECISION_COMPANY', 'ROLE_WATCH_DECISION_DEPARTMENT')")
    public ResponseData<List<CountDecisionResponse>> getCountDecision(@RequestBody EmployeeFilter filter) {
        List<CountDecisionResponse> response = decisionService.getCountDecision(filter);
        return ResponseData.<List<CountDecisionResponse>>builder()
                .code(1000)
                .data(response)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/reward-and-penalty-decision-employees")
    @PreAuthorize("hasAnyAuthority('ROLE_CREATE_DECISION')")
    public ResponseData<Void> updateRewardAndPenaltyDecisionEmployee(@RequestBody List<RewardAndPenaltyDecisionRequest> requests) {
        decisionService.updateRewardAndPenaltyDecisionEmployee(requests);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/reward-and-penalty-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_DECISION', 'ROLE_CREATE_DECISION')")
    public ResponseData<Void> updateRewardAndPenaltyDecision(@RequestBody RewardAndPenaltyDecisionRequest request) {
        decisionService.updateRewardAndPenaltyDecision(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/transfer-and-appointment-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_DECISION', 'ROLE_CREATE_DECISION')")
    public ResponseData<Void> updateTransferAndAppointmentDecision(@RequestBody TransferDecisionRequest request) {
        decisionService.updateTransferAndAppointmentDecision(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/salary-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_DECISION', 'ROLE_CREATE_DECISION')")
    public ResponseData<Void> updateSalaryDecision(@RequestBody SalaryDecisionRequest request) {
        decisionService.updateSalaryDecision(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/termination-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_DECISION', 'ROLE_CREATE_DECISION')")
    public ResponseData<Void> terminationDecision(@RequestBody TerminationDecisionRequest request) {
        decisionService.updateTerminationDecision(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/get-decision-by-id")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_DECISION_COMPANY', 'ROLE_WATCH_DECISION_DEPARTMENT')")
    public ResponseData<Object> getDecisionById(@RequestParam long decisionId) {
        Object response =  decisionService.getDecisionById(decisionId);
        return ResponseData.builder()
                .code(1000)
                .data(response)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/delete-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_DECISION')")
    public ResponseData<Void> deleteDecision(@RequestParam long decisionId) {
        decisionService.deleteDecision(decisionId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/no-approve-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_DECISION')")
    public ResponseData<Void> noApproveDecision(@RequestParam long decisionId) {
        decisionService.noApproveDecision(decisionId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("approve successfully")
                .build();
    }

    @PostMapping("/approve-decision")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_DECISION')")
    public ResponseData<Void> approveDecision(@RequestParam long decisionId) {
        decisionService.approveDecision(decisionId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("approve successfully")
                .build();
    }
}
