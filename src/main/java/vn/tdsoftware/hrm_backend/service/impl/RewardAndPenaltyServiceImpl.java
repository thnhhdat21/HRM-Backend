package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.RewardAndPenaltyDAO;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.request.RewardAndPenaltyRequest;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response.RewardAndPenaltyResponse;
import vn.tdsoftware.hrm_backend.entity.RewardAndPenalty;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RewardAndPenaltyMapper;
import vn.tdsoftware.hrm_backend.repository.RewardAndPenaltyRepository;
import vn.tdsoftware.hrm_backend.service.RewardAndPenaltyService;
import vn.tdsoftware.hrm_backend.util.constant.DecisionConstant;
import vn.tdsoftware.hrm_backend.util.constant.StatusContract;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardAndPenaltyServiceImpl implements RewardAndPenaltyService {
    private final RewardAndPenaltyDAO rewardAndPenaltyDAO;
    private final RewardAndPenaltyRepository rewardAndPenaltyRepository;

    @Override
    public List<RewardAndPenaltyResponse> getList(int type) {
        List<RewardAndPenaltyResponse> list = rewardAndPenaltyDAO.getList(type);
        if (list == null || list.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_REWARD_OR_PENALTY_IS_EMPTY);
        }
        return list;
    }

    @Override
    @Transactional
    public List<RewardAndPenaltyResponse> createNew(List<RewardAndPenaltyRequest> request) {
        List<RewardAndPenaltyResponse> response = new ArrayList<>();
        for (RewardAndPenaltyRequest requestItem : request) {
            validator(requestItem);
            RewardAndPenalty itemSaved = rewardAndPenaltyRepository.save(RewardAndPenalty.builder()
                    .name(requestItem.getName())
                    .amount(requestItem.getAmount())
                    .type(requestItem.getType())
                    .description(requestItem.getDes())
                    .status(StatusContract.ACTIVE)
                    .build());
            response.add(RewardAndPenaltyMapper.mapToRewardAndPenaltyResponse(itemSaved));
        }
        return response;
    }

    @Override
    public RewardAndPenaltyResponse update(RewardAndPenaltyRequest request) {
        RewardAndPenalty entity = rewardAndPenaltyRepository
                .findByIdAndIsEnabled(request.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.REWARD_OR_PENALTY_IS_EMPTY));
        validator(request);
        entity.setName(request.getName());
        entity.setAmount(request.getAmount());
        entity.setDescription(request.getDes());
        return RewardAndPenaltyMapper.mapToRewardAndPenaltyResponse(rewardAndPenaltyRepository.save(entity));
    }

    @Override
    public void delete(long id) {
        RewardAndPenalty entity = rewardAndPenaltyRepository
                .findByIdAndIsEnabled(id, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.REWARD_OR_PENALTY_IS_EMPTY));
        entity.setEnabled(false);
        rewardAndPenaltyRepository.save(entity);
    }

    private void validator(RewardAndPenaltyRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        } else if (request.getAmount() <= 0){
            throw new BusinessException(ErrorCode.AMOUNT_INVALID);
        }
    }
}
