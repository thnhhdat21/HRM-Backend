package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.decision.response.CountDecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.DecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.RewardAndPenaltyOfEmployee;
import vn.tdsoftware.hrm_backend.dto.decision.rewardandpenalty.request.RewardAndPenaltyDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.salary.request.SalaryDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.termination.resquest.TerminationDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.transferandappoint.request.TransferDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;

import java.time.YearMonth;
import java.util.List;

public interface DecisionService {
    List<DecisionResponse> getListDecision(EmployeeFilter filter);
    List<CountDecisionResponse> getCountDecision(EmployeeFilter filter);
    void updateRewardAndPenaltyDecisionEmployee(List<RewardAndPenaltyDecisionRequest> request);
    void updateRewardAndPenaltyDecision(RewardAndPenaltyDecisionRequest request);
    void updateTransferAndAppointmentDecision(TransferDecisionRequest request);
    void updateSalaryDecision(SalaryDecisionRequest request);
    void updateTerminationDecision(TerminationDecisionRequest request);
    void deleteDecision(long decisionId);
    Object getDecisionById(long decisionId);
    void noApproveDecision(long decisionId);
    void approveDecision(long decisionId);
    List<RewardAndPenaltyOfEmployee> getListRewardAndPenalty(long employeeId, YearMonth yearMonth);
}
