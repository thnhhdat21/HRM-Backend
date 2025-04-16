package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.AssetGroupDAO;
import vn.tdsoftware.hrm_backend.dto.assetgroup.response.AssetGroupResponse;
import vn.tdsoftware.hrm_backend.entity.AssetGroup;
import vn.tdsoftware.hrm_backend.mapper.response.assetgroup.AssetGroupResponseMapper;

import java.util.List;

@Component
public class AssetGroupDAOImpl extends AbstractDao<AssetGroup> implements AssetGroupDAO {

    @Override
    public List<AssetGroupResponse> getList() {
        String sql = "select child.id, " +
                    "child.name, " +
                    "parent.id as parentId, " +
                    "parent.name as parentName, " +
                    "child.status " +
                    "from assetGroup child left join assetGroup parent on child.parentId = parent.id " +
                    "where child.isEnabled  = true;";
        return query(sql, new AssetGroupResponseMapper());
    }

    @Override
    public void delete(long id) {
        String sql = "update assetGroup set isEnabled = false where id = ?";
        update(sql, id);
    }
}
