package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.DecisionDAO;
import vn.tdsoftware.hrm_backend.dto.contract.response.EndJobCurrentDate;
import vn.tdsoftware.hrm_backend.dto.decision.response.CountDecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.DecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.RewardAndPenaltyOfEmployee;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.entity.Decision;
import vn.tdsoftware.hrm_backend.mapper.RewardAndPenaltyMapper;
import vn.tdsoftware.hrm_backend.mapper.response.contract.EndJobCurrentDateMapper;
import vn.tdsoftware.hrm_backend.mapper.response.decision.CountDecisionMapper;
import vn.tdsoftware.hrm_backend.mapper.response.decision.DecisionResponseMapper;
import vn.tdsoftware.hrm_backend.mapper.response.decision.RewardAndPenaltyOfEmployeeMapper;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.time.LocalDate;
import java.util.List;

@Component
public class DecisionDAOImpl extends AbstractDao<Decision> implements DecisionDAO {

    @Override
    public List<DecisionResponse> getListDecision(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder(
                "SELECT decisionList.createdBy," +
                        "decisionList.decisionId,  " +
                        "decisionList.employeeName, " +
                        "decisionList.decisionCode, " +
                        "decisionList.decisionDate, " +
                        "decisionList.decisionType, " +
                        "decisionList.decisionState, " +
                        "decisionList.createdAt " +
                        "FROM ( " +
                        "SELECT ROW_NUMBER() OVER (ORDER BY decision.createdAt DESC) AS RowNumber," +
                        "decision.id as decisionId, " +
                        "ec.fullName as createdBy, " +
                        "employee.fullName as employeeName, " +
                        "decision.code as decisionCode, " +
                        "decision.date as decisionDate, " +
                        "decision.type as decisionType, " +
                        "decision.state as decisionState, " +
                        "decision.createdAt  " +
                        "from decision  " +
                        "inner join employee on decision.employeeId = employee.id  " +
                        "inner join employee ec on decision.createdBy = ec.id  " +
                        "inner join account on employee.id = account.employeeId  " +
                        "inner join contractGeneral  on employee.id = contractGeneral.employeeId " +
                        "inner join department on contractGeneral.departmentId = department.id   " +
                        "inner join jobPosition on contractGeneral.jobPositionId = jobPosition.id " +
                        "inner join duty on jobPosition.dutyId = duty.id " +
                        "where decision.isEnabled = true ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_DECISION));
        int index = filter.getPageIndex() == 0 ? 1 : filter.getPageIndex();
        sql.append(" ) AS decisionList " +
                "  WHERE decisionList.RowNumber BETWEEN ").append((index -1)*12 + 1).append(" AND ").append((index -1)*12 + 12);
        return query(sql.toString(), new DecisionResponseMapper());
    }

    @Override
    public List<CountDecisionResponse> getCountDecision(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder("select " +
                "decisionState.id, " +
                "decisionState.name, " +
                "COALESCE(decisionList.count, 0) as count " +
                "from decisionState left join " +
                "(select decision.state, count(decision.id) as count " +
                "from decision  " +
                "inner join employee on decision.employeeId = employee.id  " +
                "inner join employee ec on decision.createdBy = ec.id  " +
                "inner join account on employee.id = account.employeeId  " +
                "inner join contractGeneral  on employee.id = contractGeneral.employeeId " +
                "inner join department on contractGeneral.departmentId = department.id   " +
                "inner join jobPosition on contractGeneral.jobPositionId = jobPosition.id " +
                "inner join duty on jobPosition.dutyId = duty.id " +
                "where decision.isEnabled = true ");
        sql.append(SQLUtil.sqlFilter(filter, FilterConstant.TYPE_DECISION));
        sql.append(" group by decision.state ) AS decisionList on decisionState.id = decisionList.state ");
        return query(sql.toString(), new CountDecisionMapper());
    }

    @Override
    public List<RewardAndPenaltyOfEmployee> getListRewardAndPenalty(long employeeId, LocalDate startDate, LocalDate endDate) {
        String sql = "select decision.type, " +
                "rewardAndPenaltyDecision.amount " +
                "from decision " +
                "inner join rewardAndPenaltyDecision on decision.id = rewardAndPenaltyDecision.decisionId " +
                "where decision.isEnabled = 1 and decision.employeeId = ? and  decision.date between '" +
                startDate + "' and '" + endDate + "'";
        return query(sql, new RewardAndPenaltyOfEmployeeMapper(), employeeId);
    }

    @Override
    public List<EndJobCurrentDate> getListDecisionCurrentDate(LocalDate dateCurrent) {
        String sql = "select  " +
                "contractgeneral.contractOriginal as contractId, " +
                "decision.date as currentDate, " +
                "decision.reason as reason " +
                " from decision " +
                " inner join employee on decision.employeeId = employee.id  " +
                " inner join contractgeneral on employee.id = contractgeneral.employeeId " +
                " where decision.isEnabled = 1 " +
                " and decision.type = 6  " +
                " and decision.state = 2 " +
                " and decision.date = '" + dateCurrent + "'";
        return query(sql, new EndJobCurrentDateMapper());
    }
}
