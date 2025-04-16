package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.InsuranceRatioDAO;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.request.InsuranceRatioRequest;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.request.RatioDetailRequest;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioDetailResponse;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioResponse;
import vn.tdsoftware.hrm_backend.entity.InsuranceRatio;
import vn.tdsoftware.hrm_backend.entity.InsuranceRatioDetail;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.InsuranceRatioMapper;
import vn.tdsoftware.hrm_backend.repository.InsuranceRatioDetailRepository;
import vn.tdsoftware.hrm_backend.repository.InsuranceRatioRepository;
import vn.tdsoftware.hrm_backend.service.InsuranceRatioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InsuranceRatioServiceImpl implements InsuranceRatioService {
    private final InsuranceRatioRepository insuranceRatioRepository;
    private final InsuranceRatioDetailRepository insuranceRatioDetailRepository;
    private final InsuranceRatioDAO insuranceRatioDAO;

    @Override
    public List<InsuranceRatioResponse> getInsuranceRatio() {
        List<InsuranceRatio> responses = insuranceRatioRepository.findAllByIsEnabled(true);
        if (responses.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_INSURANCE_RATIO_IS_EMPTY);
        }
        List<InsuranceRatioResponse> insuranceRatioResponses = new ArrayList<>();
        for (InsuranceRatio insuranceRatio : responses) {
            insuranceRatioResponses.add(InsuranceRatioMapper.maptoInsuranceRatioResponse(insuranceRatio));
        }
        return insuranceRatioResponses;
    }

    @Override
    public InsuranceRatioDetailResponse getInsuranceRatioDetail(int id) {
        InsuranceRatioDetailResponse response = insuranceRatioDAO.getAllInsuranceRatioById(id);
        if (response == null) {
            throw new BusinessException(ErrorCode.LIST_INSURANCE_RATIO_IS_EMPTY);
        }
        return response;
    }

    @Override
    @Transactional
    public List<InsuranceRatioResponse> createInsuranceRatio(List<InsuranceRatioRequest> insuranceRatioRequest) {
        List<InsuranceRatioResponse> insuranceRatioResponses = new ArrayList<>();
        for (InsuranceRatioRequest item : insuranceRatioRequest) {
            InsuranceRatio insuranceRatioSaved = insuranceRatioRepository.save(InsuranceRatio.builder()
                            .dateStart(item.getDateStart())
                    .build());
            insuranceRatioResponses.add(InsuranceRatioMapper.maptoInsuranceRatioResponse(insuranceRatioSaved));
            for (RatioDetailRequest ratioItem : item.getRatios()) {
                insuranceRatioDetailRepository.save(InsuranceRatioDetail.builder()
                                .insuranceRatioId(insuranceRatioSaved.getId())
                                .insuranceType(ratioItem.getType())
                                .ratio(ratioItem.getRatio())
                                .companyPay(ratioItem.getCompanyPay())
                        .build());
            }
        }
        return insuranceRatioResponses;
    }

    @Override
    public void updateInsuranceRatio(List<InsuranceRatioRequest> insuranceRatioRequest) {
        for (InsuranceRatioRequest item : insuranceRatioRequest) {
            InsuranceRatio insuranceRatioEntity = insuranceRatioRepository.findByIdAndIsEnabled(item.getId(), true).orElseThrow(
                    () -> new BusinessException(ErrorCode.INSURANCE_RATIO_INVALID)
            );
            insuranceRatioEntity.setDateStart(item.getDateStart());
            insuranceRatioRepository.save(insuranceRatioEntity);
            for (RatioDetailRequest ratioItem : item.getRatios()) {
                if (ratioItem.isHasUpdate()) {
                    InsuranceRatioDetail insuranceRatioDetail = insuranceRatioDetailRepository.findByIdAndIsEnabled(ratioItem.getDetailId(), true).orElseThrow(
                            () -> new BusinessException(ErrorCode.INSURANCE_RATIO_INVALID)
                    );
                    insuranceRatioDetail.setRatio(ratioItem.getRatio());
                    insuranceRatioDetail.setCompanyPay(ratioItem.getCompanyPay());
                    insuranceRatioDetailRepository.save(insuranceRatioDetail);
                }
            }
        }
    }

    @Override
    public void deleteInsuranceRatio(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_INSURANCE_RATIO_IS_EMPTY);
        }
        StringBuilder listId = new StringBuilder();
        for (int i = 0; i < requests.size(); i++) {
            listId.append(requests.get(i));
            if (i < requests.size() - 1) {
                listId.append(",");
            }
        }
        insuranceRatioDAO.deleteInsuranceRatioDetail(listId.toString());
        insuranceRatioDAO.deleteInsuranceRatio(listId.toString());
    }

}
