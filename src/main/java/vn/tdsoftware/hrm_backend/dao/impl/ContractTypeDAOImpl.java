package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.ContractTypeDAO;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeDetail;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.entity.ContractType;
import vn.tdsoftware.hrm_backend.mapper.response.CountContractTypeMapper;
import vn.tdsoftware.hrm_backend.mapper.response.contractype.ContractTypeDetailMapper;
import vn.tdsoftware.hrm_backend.util.SQLUtil;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.util.List;

@Component
public class ContractTypeDAOImpl extends AbstractDao<ContractType> implements ContractTypeDAO {

    @Override
    public ContractTypeDetail getContractType(long id) {
        String sql = "select contractType.id, " +
                "contractType.name, " +
                "contractType.type, " +
                "contractType.method, " +
                "contractType.term, " +
                "contractType.unit," +
                "contractType.insurance, " +
                "COALESCE(GROUP_CONCAT(allowance.id), '') as allowances " +
                "from contractType  " +
                "left join contractTypeHasAllowance on contractType.id = contractTypeHasAllowance.contractTypeId " +
                "left join allowance on contractTypeHasAllowance.allowanceId  = allowance.id and contractTypeHasAllowance.isEnabled = true " +
                "where contractType.isEnabled = true and contractType.id = ?;";
        List<ContractTypeDetail> list = query(sql, new ContractTypeDetailMapper(), id);
        return list.get(0);
    }

    @Override
    public void deleteAllowance(long id) {
        String sql = "update contractTypeHasAllowance set isEnabled = 0 where contractTypeId = ?";
        update(sql, id);
    }

}
