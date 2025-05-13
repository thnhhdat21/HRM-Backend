package vn.tdsoftware.hrm_backend.dao.history.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.history.ContractHistoryDAO;
import vn.tdsoftware.hrm_backend.dao.impl.AbstractDao;
import vn.tdsoftware.hrm_backend.dto.history.contract.response.ContractHistoryResponse;
import vn.tdsoftware.hrm_backend.entity.history.ContractHistory;
import vn.tdsoftware.hrm_backend.mapper.response.history.contract.ContractHistoryResponseMapper;

import java.util.List;

@Component
public class ContractHistoryDAOImpl extends AbstractDao<ContractHistory> implements ContractHistoryDAO {

    @Override
    public List<ContractHistoryResponse> getContractHistory(long contractId) {
        String sql = "select ch.title,  " +
                "ch.state,  " +
                "ch.createdAt,  " +
                "e.fullName as createdBy  " +
                "from contractHistory ch   " +
                "inner join employee e on ch.createdBy = e.id   " +
                "where ch.isEnabled = true  " +
                "and ch.contractId = ?";
        return query(sql, new ContractHistoryResponseMapper(), contractId);
    }
}
