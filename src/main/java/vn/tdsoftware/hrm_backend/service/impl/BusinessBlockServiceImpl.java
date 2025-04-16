package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.BusinessBlockDAO;
import vn.tdsoftware.hrm_backend.dto.businessblock.request.BusinessBlockRequest;
import vn.tdsoftware.hrm_backend.dto.businessblock.response.BusinessBlockResponse;
import vn.tdsoftware.hrm_backend.entity.BusinessBlock;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.BusinessBlockMapper;
import vn.tdsoftware.hrm_backend.repository.BusinessBlockRepository;
import vn.tdsoftware.hrm_backend.service.BusinessBlockService;
import vn.tdsoftware.hrm_backend.util.FieldStringUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessBlockServiceImpl implements BusinessBlockService {

    private final BusinessBlockDAO businessBlockDAO;
    private final BusinessBlockRepository businessBlockRepository;

    @Override
    public List<BusinessBlockResponse> getListBusinessBlock() {
        List<BusinessBlockResponse> listBusinessBlockResponse = businessBlockDAO.findAll();
        if (listBusinessBlockResponse.isEmpty()) {
            throw  new BusinessException(ErrorCode.LIST_BUSINESS_BLOCK_IS_EMPTY);
        }
        return listBusinessBlockResponse;
    }

    @Override
    public BusinessBlockResponse createBusinessBlock(BusinessBlockRequest request) {
        FieldStringUtil.validatorNameAndCode(request.getName(), request.getName());
        BusinessBlock businessBlockSaved = businessBlockRepository.save(BusinessBlock.builder()
                        .name(request.getName())
                        .code(request.getCode())
                .build());
        return BusinessBlockMapper.mapToBusinessBlockResponse(businessBlockSaved);
    }

    @Override
    public BusinessBlockResponse updateBusinessBlock(BusinessBlockRequest request) {
        FieldStringUtil.validatorNameAndCode(request.getName(), request.getName());
        BusinessBlock businessBlock = businessBlockRepository.findBusinessBlockByIdAndIsEnabled(request.getId(), true);
        if (businessBlock == null) {
            throw new BusinessException(ErrorCode.BUSINESS_BLOCK_IS_EMPTY);
        }
        businessBlock.setName(request.getName());
        businessBlock.setCode(request.getCode());
        return BusinessBlockMapper.mapToBusinessBlockResponse(businessBlockRepository.save(businessBlock));
    }

    @Override
    public void deleteBusinessBlock(int id) {
        int countEmployee = businessBlockDAO.countEmployeeOfBlock(id);
        if (countEmployee > 0) {
            throw new BusinessException(ErrorCode.DELETE_BUSINESS_BLOCK_ERROR);
        }
        businessBlockDAO.deleteDepartmentOfBlock(id);
        businessBlockDAO.deleteBusinessBlock(id);
    }
}
