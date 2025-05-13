package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.contract.response.EndJobCurrentDate;
import vn.tdsoftware.hrm_backend.dto.decision.response.CountDecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.DecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.RewardAndPenaltyOfEmployee;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DecisionDAO {
    List<DecisionResponse> getListDecision(EmployeeFilter filter);
    List<CountDecisionResponse> getCountDecision(EmployeeFilter filter);
    List<RewardAndPenaltyOfEmployee> getListRewardAndPenalty(long employeeId, LocalDate startDate, LocalDate endDate);
    List<EndJobCurrentDate> getListDecisionCurrentDate(LocalDate dateCurrent);
}
