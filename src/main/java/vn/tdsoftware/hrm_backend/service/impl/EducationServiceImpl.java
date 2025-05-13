package vn.tdsoftware.hrm_backend.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.education.request.EducationRequest;
import vn.tdsoftware.hrm_backend.dto.education.response.EducationResponse;
import vn.tdsoftware.hrm_backend.entity.Education;
import vn.tdsoftware.hrm_backend.entity.Employee;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.EducationMapper;
import vn.tdsoftware.hrm_backend.repository.EducationRepository;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;
import vn.tdsoftware.hrm_backend.service.EducationService;
import vn.tdsoftware.hrm_backend.util.PerEmployeeUtil;
import vn.tdsoftware.hrm_backend.util.constant.UpdateTypeConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    private final EmployeeRepository employeeRepository;
    private final PerEmployeeUtil perEmployeeUtil;

    @Override
    public List<EducationResponse> getEducationProfile(long employeeId) {
        perEmployeeUtil.checkWatchSameDepartmentByEmployeeId(employeeId);
        employeeRepository.findByIdAndIsEnabled(employeeId, true).orElseThrow(
                () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
        );
        List<Education> listEducationEntity = educationRepository.findAllByEmployeeIdAndIsEnabled(employeeId, true)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new BusinessException(ErrorCode.LIST_EDUCATION_IS_EMPTY)
                );
        List<EducationResponse> responseList = new ArrayList<>();
        for (Education education : listEducationEntity) {
            responseList.add(EducationMapper.mapToEducationResponse(education));
        }
        return responseList;
    }

    @Override
    public void updateEducationProfile(List<EducationRequest> requests) {
        perEmployeeUtil.checkUpdateSameDepartmentByEmployeeId(requests.get(0).getEmployeeId());
        if (requests.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_EDUCATION_REQUEST_IS_EMPTY);
        }
        List<Education> educationList = new ArrayList<>();
        for (EducationRequest request : requests) {
            Employee employee = employeeRepository.findByIdAndIsEnabled(request.getEmployeeId(), true).orElseThrow(
                    () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
            );
            getEduSave(employee, educationList, request);
        }
        educationRepository.saveAll(educationList);
    }

    @Override
    public void updateEducationProfile(List<EducationRequest> requests, Employee employee) {
        if (requests.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_EDUCATION_REQUEST_IS_EMPTY);
        }
        List<Education> educationList = new ArrayList<>();
        for (EducationRequest request : requests) {
            getEduSave(employee, educationList, request);
        }
        educationRepository.saveAll(educationList);
    }

    private void getEduSave(Employee employee, List<Education> educationList, EducationRequest request) {
        Education education = new Education();
        if (request.getId() != null) {
            education = educationRepository.findByIdAndIsEnabled(request.getId(), true).orElseThrow(
                    () -> new BusinessException(ErrorCode.EDUCATION_IS_EMPTY)
            );
        }
        if(Objects.equals(request.getIsUpdate(), UpdateTypeConstant.UPDATE)) {
            checkValidatorRequest(request);
            education.setEmployeeId(employee.getId());
            education.setToMonth(request.getToMonth());
            education.setFromMonth(request.getFromMonth());
            education.setLevel(request.getLevel());
            education.setPlaceTraining(request.getPlaceTraining());
            education.setMajor(request.getMajor());
            education.setMethodTraining(request.getMethodTraining());
            educationList.add(education);
        } else if (Objects.equals(request.getIsUpdate(), UpdateTypeConstant.DELETE)) {
            education.setEnabled(false);
            educationList.add(education);
        }
    }


    private void checkValidatorRequest(EducationRequest request) {
        if (request.getToMonth() == null || request.getToMonth().toString().isEmpty()) {
            throw new BusinessException(ErrorCode.TO_MONTH_IS_EMPTY);
        }else if (request.getFromMonth() == null || request.getFromMonth().toString().isEmpty()) {
            throw new BusinessException(ErrorCode.FROM_MONTH_IS_EMPTY);
        } else if(request.getLevel() == null || request.getLevel().isEmpty()) {
            throw new BusinessException(ErrorCode.EDUCATION_LEVEL_INVALID);
        }  else if (request.getPlaceTraining() == null || request.getPlaceTraining().isEmpty()) {
            throw new BusinessException(ErrorCode.PLACE_TRAINING_IS_EMPTY);
        } else if (request.getMajor() == null || request.getMajor().isEmpty()) {
            throw new BusinessException(ErrorCode.MAJOR_IS_EMPTY);
        } else if (request.getMethodTraining() == null || request.getMethodTraining().toString().isEmpty()) {
            throw new BusinessException(ErrorCode.METHOD_TRAINING_IS_EMPTY);
        }
    }
}