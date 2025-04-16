package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.businessblock.response.BusinessBlockResponse;

import java.util.List;

@Repository
public interface BusinessBlockDAO {
    List<BusinessBlockResponse> findAll();
    int countEmployeeOfBlock(int id);
    void deleteDepartmentOfBlock(int businessBlockId);
    void deleteBusinessBlock(int id);
}
