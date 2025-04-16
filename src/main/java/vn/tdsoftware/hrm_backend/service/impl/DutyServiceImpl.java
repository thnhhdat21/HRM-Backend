package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.DutyDAO;
import vn.tdsoftware.hrm_backend.dto.duty.request.DutyRequest;
import vn.tdsoftware.hrm_backend.dto.duty.response.DutyResponse;
import vn.tdsoftware.hrm_backend.entity.Duty;
import vn.tdsoftware.hrm_backend.entity.JobPosition;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.DutyMapper;
import vn.tdsoftware.hrm_backend.repository.DutyRepository;
import vn.tdsoftware.hrm_backend.repository.JobPositionRepository;
import vn.tdsoftware.hrm_backend.service.DutyService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;
    private final DutyDAO dutyDAO;
    private final JobPositionRepository jobPositionRepository;

    @Override
    public List<DutyResponse> getListDuty() {
        List<DutyResponse> listDuty = dutyDAO.getListDuty();
        if (listDuty.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_DUTY_IS_EMPTY);
        }
        return listDuty;
    }

    @Override
    @Transactional
    public List<DutyResponse> createDuty(List<DutyRequest> dutyRequest) {
        List<DutyResponse> listResponse = new ArrayList<>();
        for (DutyRequest request : dutyRequest) {
            if(request.getName().trim().length() < 5)
                throw new BusinessException(ErrorCode.NAME_DUTY_INVALID);

            Duty dutySaved =  dutyRepository.save(Duty.builder()
                            .name(request.getName().trim())
                            .description(request.getDes().trim())
                            .status("Hoạt động")
                    .build());
            listResponse.add(DutyMapper.mapToDutyResponse(dutySaved));
        }
        return listResponse;
    }

    @Override
    public DutyResponse updateDuty(Long id, String name, String des) {
        Duty dutySaved = checkDuty(id);
        if(name.trim().length() < 5) {
            throw new BusinessException(ErrorCode.NAME_DUTY_INVALID);
        }
        dutySaved.setName(name);
        dutySaved.setDescription(des);
        return DutyMapper.mapToDutyResponse(dutyRepository.save(dutySaved));
    }

    @Override
    public void deleteDuty(long id) {
        checkDuty(id);
        dutyDAO.deleteDuty(id);
    }


    private Duty checkDuty(Long id) {
        if (id < 0)
            throw new BusinessException(ErrorCode.ID_NOT_EXIST);
        Duty dutySaved = dutyRepository.findByIdAndIsEnabled(id, true);
        if (dutySaved == null)
            throw new BusinessException(ErrorCode.DUTY_NOT_EXIST);
        List<JobPosition> jobPositionList = jobPositionRepository.findByDutyIdAndIsEnabled(dutySaved.getId(), true);
        if (!jobPositionList.isEmpty()) {
            throw new BusinessException(ErrorCode.DELETE_DUTY_ERROR_DUE_JOB_POSITION);
        }
        return dutySaved;
    }
}
