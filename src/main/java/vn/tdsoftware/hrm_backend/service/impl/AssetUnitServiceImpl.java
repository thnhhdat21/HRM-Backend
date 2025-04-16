package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.assetunit.request.AssetUnitRequest;
import vn.tdsoftware.hrm_backend.dto.assetunit.response.AssetUnitResponse;
import vn.tdsoftware.hrm_backend.entity.AssetUnit;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.AssetUnitMapper;
import vn.tdsoftware.hrm_backend.repository.AssetUnitRepository;
import vn.tdsoftware.hrm_backend.service.AssetUnitService;
import vn.tdsoftware.hrm_backend.util.constant.StatusContract;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetUnitServiceImpl implements AssetUnitService {
    private final AssetUnitRepository assetUnitRepository;

    @Override
    public List<AssetUnitResponse> getList() {
        List<AssetUnit> assetUnits = assetUnitRepository.findAllByIsEnabled(true);
        if (assetUnits.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_ASSET_UNIT_IS_EMPTY);
        }
        List<AssetUnitResponse> assetUnitResponses = new ArrayList<>();
        for (AssetUnit assetUnit : assetUnits) {
            assetUnitResponses.add(AssetUnitMapper.mapToAssetUnitResponse(assetUnit));
        }
        return assetUnitResponses;
    }

    @Override
    @Transactional
    public List<AssetUnitResponse> createAssetUnit(List<AssetUnitRequest> request) {
        List<AssetUnitResponse> assetUnitResponses = new ArrayList<>();
        for (AssetUnitRequest assetUnitRequest : request) {
            if (assetUnitRequest.getName() == null || assetUnitRequest.getName().isEmpty()) {
                throw new BusinessException(ErrorCode.NAME_INVALID);
            }
            assetUnitResponses.add(AssetUnitMapper.mapToAssetUnitResponse(assetUnitRepository.save(AssetUnit.builder()
                            .name(assetUnitRequest.getName())
                            .status(StatusContract.ACTIVE)
                    .build())));
        }
        return assetUnitResponses;
    }

    @Override
    public AssetUnitResponse updateAssetUnit(AssetUnitRequest request) {
        AssetUnit assetUnitEntity = assetUnitRepository.findByIdAndIsEnabled(request.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.ASSET_UNIT_IS_EMPTY)
        );
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        }
        assetUnitEntity.setName(request.getName());
        return AssetUnitMapper.mapToAssetUnitResponse(assetUnitRepository.save(assetUnitEntity));
    }

    @Override
    public void deleteAssetUnit(long id) {
        AssetUnit assetUnitEntity = assetUnitRepository.findByIdAndIsEnabled(id, true).orElseThrow(
                () -> new BusinessException(ErrorCode.ASSET_UNIT_IS_EMPTY)
        );
        assetUnitEntity.setEnabled(false);
        assetUnitRepository.save(assetUnitEntity);
    }
}
