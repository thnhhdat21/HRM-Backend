package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.AssetGroupDAO;
import vn.tdsoftware.hrm_backend.dto.assetgroup.request.AssetGroupRequest;
import vn.tdsoftware.hrm_backend.dto.assetgroup.response.AssetGroupResponse;
import vn.tdsoftware.hrm_backend.entity.AssetGroup;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.AssetGroupMapper;
import vn.tdsoftware.hrm_backend.repository.AssetGroupRepository;
import vn.tdsoftware.hrm_backend.service.AssetGroupService;
import vn.tdsoftware.hrm_backend.util.constant.StatusContract;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetGroupServiceImpl implements AssetGroupService {
    private final AssetGroupDAO assetGroupDAO;
    private final AssetGroupRepository assetGroupRepository;

    @Override
    public List<AssetGroupResponse> getList() {
        List<AssetGroupResponse> assetGroupResponseList = assetGroupDAO.getList();
        if (assetGroupResponseList == null) {
            throw new BusinessException(ErrorCode.LIST_ASSET_GROUP_IS_EMPTY);
        }
        return assetGroupResponseList;
    }

    @Override
    public AssetGroupResponse createAssetGroup(AssetGroupRequest assetGroupRequest) {
        if (assetGroupRequest.getName() == null || assetGroupRequest.getName().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        }
        AssetGroup assetGroupSaved = assetGroupRepository.save(AssetGroup.builder()
                        .name(assetGroupRequest.getName())
                        .parentId(assetGroupRequest.getParentId())
                        .status(StatusContract.ACTIVE)
                .build());
        return AssetGroupMapper.mapToAssetGroupResponse(assetGroupSaved);
    }

    @Override
    public AssetGroupResponse updateAssetGroup(AssetGroupRequest assetGroupRequest) {
        AssetGroup assetGroup = checkAssetGroup(assetGroupRequest.getId(), ErrorCode.ASSET_GROUP_IS_EMPTY);
        if (assetGroupRequest.getParentId() != null) {
            checkAssetGroup(assetGroupRequest.getParentId(), ErrorCode.ASSET_GROUP_PARENT_IS_EMPTY);
            assetGroup.setParentId(assetGroupRequest.getParentId());
        }
        if (assetGroupRequest.getName() == null || assetGroupRequest.getName().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        }
        assetGroup.setName(assetGroupRequest.getName());
        return AssetGroupMapper.mapToAssetGroupResponse(assetGroupRepository.save(assetGroup));
    }

    @Override
    public void deleteAssetGroup(long id) {
        if (assetGroupRepository.existsByParentIdAndIsEnabled(id, true)) {
            throw new BusinessException(ErrorCode.DELETE_ASSET_GROUP_ERROR);
        }
        assetGroupDAO.delete(id);
    }


    private AssetGroup checkAssetGroup(Long id, ErrorCode errorCode) {
        return assetGroupRepository.findByIdAndIsEnabled(id, true).orElseThrow(
                () ->  new BusinessException(errorCode));
    }
}
