package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.ContractGeneralDAO;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractGeneralDetail;
import vn.tdsoftware.hrm_backend.entity.ContractGeneral;
import vn.tdsoftware.hrm_backend.mapper.response.contract.ContractGeneralDetailMapper;

@Component
public class ContractGeneralDAOImpl extends AbstractDao<ContractGeneral> implements ContractGeneralDAO {

    @Override
    public ContractGeneralDetail getContractGeneralDetail(long employeeId) {
        String sql = "select contractGeneral.salaryGross," +
                "contractGeneral.contractOriginal as contractId," +
                "duty.isAutoCheckin, " +
                "contractGeneralHasAllowance.id as contractHasAllowanceId," +
                "department.id as department, " +
                "jobPosition.id as jobPosition,  " +
                "allowance.id as allowanceId,  " +
                "contractGeneralHasAllowance.amount as allowanceAmount,  " +
                "contractGeneralHasAllowance.unit as allowanceUnit  " +
                "from contractGeneral " +
                "inner join department on contractGeneral.departmentId = department.id " +
                "inner join jobPosition on contractGeneral.jobPositionId = jobPosition.id " +
                "inner join duty on jobPosition.dutyId = duty.id " +
                "left join contractGeneralHasAllowance on contractGeneral.id = contractGeneralHasAllowance.contractId " +
                "left join allowance on contractGeneralHasAllowance.allowanceId = allowance.id " +
                "where contractGeneral.employeeId = ? ";
        return queryDetails(sql, new ContractGeneralDetailMapper(), employeeId).get(0);
    }
}
