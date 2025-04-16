package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.JobPositionDAO;
import vn.tdsoftware.hrm_backend.dto.jobposition.request.JobPositionRequest;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionDetailResponse;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionResponse;
import vn.tdsoftware.hrm_backend.entity.Duty;
import vn.tdsoftware.hrm_backend.entity.JobPosition;
import vn.tdsoftware.hrm_backend.entity.Role;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.JobPositionMapper;
import vn.tdsoftware.hrm_backend.repository.DutyRepository;
import vn.tdsoftware.hrm_backend.repository.JobPositionRepository;
import vn.tdsoftware.hrm_backend.repository.RoleRepository;
import vn.tdsoftware.hrm_backend.service.JobPositionService;
import vn.tdsoftware.hrm_backend.util.constant.StatusContract;

import java.util.List;


@Service
@RequiredArgsConstructor
public class JobPositionServiceImpl implements JobPositionService {
    private final JobPositionDAO jobPositionDAO;
    private final JobPositionRepository jobPositionRepository;
    private final DutyRepository dutyRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<JobPositionResponse> getListJobPosition() {
        List<JobPositionResponse> jobPositions = jobPositionDAO.getListJobPosition();
        if (jobPositions.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_JOB_POSITION_IS_EMPTY);
        }
        return jobPositions;
    }

    @Override
    public JobPositionResponse createJobPosition(JobPositionRequest jobPositionRequest) {
        if (jobPositionRequest.getName().trim().isEmpty() || jobPositionRequest.getName().length() < 5) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        }
        checkJobRequest(jobPositionRequest);
        checkSalaryJobPosition(jobPositionRequest.getSalaryFrom(), jobPositionRequest.getSalaryTo());
        return JobPositionMapper.mapToJobPositionResponse(jobPositionRepository.save(JobPosition.builder()
                        .name(jobPositionRequest.getName())
                        .dutyId(jobPositionRequest.getDutyId())
                        .roleId(jobPositionRequest.getRoleId())
                        .salaryFrom(jobPositionRequest.getSalaryFrom())
                        .salaryTo(jobPositionRequest.getSalaryTo())
                        .status(StatusContract.ACTIVE)
                        .description(jobPositionRequest.getDes())
                .build()));
    }

    @Override
    public JobPositionResponse updateJobPosition(JobPositionRequest jobPositionRequest) {
        JobPosition jobPosition = jobPositionRepository.findByIdAndIsEnabled(jobPositionRequest.getId(), true);
        if (jobPosition == null) {
            throw new BusinessException(ErrorCode.JOB_POSITION_NOT_EXIST);
        }
        checkJobRequest(jobPositionRequest);
        checkSalaryJobPosition(jobPositionRequest.getSalaryFrom(), jobPositionRequest.getSalaryTo());
        jobPosition.setName(jobPositionRequest.getName());
        jobPosition.setDutyId(jobPositionRequest.getDutyId());
        jobPosition.setRoleId(jobPositionRequest.getRoleId());
        jobPosition.setSalaryFrom(jobPositionRequest.getSalaryFrom());
        jobPosition.setSalaryTo(jobPositionRequest.getSalaryTo());
        jobPosition.setDescription(jobPositionRequest.getDes());
        return JobPositionMapper.mapToJobPositionResponse(jobPositionRepository.save(jobPosition));
    }

    @Override
    public JobPositionDetailResponse getJobPositionDetail(long id) {
        JobPosition jobPosition = jobPositionRepository.findByIdAndIsEnabled(id, true);
        if (jobPosition == null) {
            throw new BusinessException(ErrorCode.JOB_POSITION_NOT_EXIST);
        }
        return JobPositionMapper.mapToJobPositionDetailResponse(jobPosition);
    }

    @Override
    public void deleteJobPosition(long id) {
        jobPositionRepository.findByIdAndIsEnabled(id, true);
        jobPositionDAO.deleteJobPosition(id);
    }

    private void checkSalaryJobPosition(int salaryFrom, int salaryTo) {
        if (salaryTo < 0 || salaryFrom >= salaryTo) {
            throw new BusinessException(ErrorCode.RANK_SALARY_INVALID);
        }
    }

    private void checkJobRequest(JobPositionRequest jobPositionRequest) {
        Duty duty = dutyRepository.findByIdAndIsEnabled(jobPositionRequest.getDutyId(), true);
        if (duty == null) {
            throw new BusinessException(ErrorCode.DUTY_NOT_EXIST);
        }
        Role role = roleRepository.findByIdAndIsEnabled(jobPositionRequest.getRoleId(), true);
        if (role == null) {
            throw new BusinessException(ErrorCode.ROLE_NOT_EXISTED);
        }
    }

}
