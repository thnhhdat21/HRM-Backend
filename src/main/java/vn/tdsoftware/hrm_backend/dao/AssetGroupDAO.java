package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.assetgroup.response.AssetGroupResponse;

import java.util.List;

@Repository
public interface AssetGroupDAO {
    List<AssetGroupResponse> getList();
    void delete(long id);
}
