package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.BusinessBlockDAO;
import vn.tdsoftware.hrm_backend.dto.businessblock.response.BusinessBlockResponse;
import vn.tdsoftware.hrm_backend.entity.BusinessBlock;
import vn.tdsoftware.hrm_backend.mapper.response.businessblock.BusinessBlockResponseMapper;

import java.util.List;

@Component
public class BusinessBlockDAOImpl extends AbstractDao<BusinessBlock> implements BusinessBlockDAO {

    @Override
    public List<BusinessBlockResponse> findAll() {
        String sqlQuery = "select businessBlock.id, businessBlock.name,businessBlock.code " +
                "from businessBlock " +
                "where businessBlock.isEnabled = 1 ";
        List<BusinessBlockResponse> businessBlocks = query(sqlQuery, new BusinessBlockResponseMapper());
        return businessBlocks;
    }

    @Override
    public int countEmployeeOfBlock(int id) {
        String sqlQuery = "select count(employee.id) as countEmployee " +
                "from businessBlock inner join department on businessBlock.id = department.businessBlockId " +
                "inner join employee on department.id = employee.departmentId " +
                "where businessBlock.id = ? ";
        return count(sqlQuery, id);
    }

    @Override
    public void deleteDepartmentOfBlock(int businessBlockId) {
        String sqlQuery = "update Department " +
                "set isEnabled = 0 " +
                "where businessBlockId  = ? ";
        update(sqlQuery, businessBlockId);
    }

    @Override
    public void deleteBusinessBlock(int id) {
        String sqlQuery = "update BusinessBlock " +
                "set isEnabled = 0 " +
                "where id  = ? ";
        update(sqlQuery, id);
    }
}
