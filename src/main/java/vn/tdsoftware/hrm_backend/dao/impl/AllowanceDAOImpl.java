package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.AllowanceDAO;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.entity.Allowance;
import vn.tdsoftware.hrm_backend.mapper.response.allowance.AllowanceResponseMapper;

import java.util.List;

@Component
public class AllowanceDAOImpl extends AbstractDao<Allowance> implements AllowanceDAO {
    @Override
    public List<AllowanceResponse> getAllowanceByContractType(long contractTypeId) {
        String sql= "select   " +
                "allowance.id,   " +
                "allowance.name,  " +
                "allowance.amount,  " +
                "allowance.unit  " +
                "from contractType   " +
                "inner join contractTypeHasAllowance  on contractType.id = contractTypeHasAllowance.contractTypeId and contractTypeHasAllowance.isEnabled = true  " +
                "inner join allowance on contractTypeHasAllowance.allowanceId = allowance.id   " +
                "where allowance.isEnabled = true  " +
                "and contractType.isEnabled = true   " +
                "and contractType.id = ?  ";
        return query(sql, new AllowanceResponseMapper(), contractTypeId);
    }
}
