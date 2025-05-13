package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.family.request.FamilyRequest;
import vn.tdsoftware.hrm_backend.dto.family.resposne.FamilyResponse;
import vn.tdsoftware.hrm_backend.entity.Employee;
import vn.tdsoftware.hrm_backend.entity.Family;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.FamilyMapper;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;
import vn.tdsoftware.hrm_backend.repository.FamilyRepository;
import vn.tdsoftware.hrm_backend.service.FamilyService;
import vn.tdsoftware.hrm_backend.util.PerEmployeeUtil;
import vn.tdsoftware.hrm_backend.util.constant.UpdateTypeConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {
    private final FamilyRepository familyRepository;
    private final EmployeeRepository employeeRepository;
    private final PerEmployeeUtil perEmployeeUtil;

    @Override
    public List<FamilyResponse> getFamilyOfEmployee(long employeeId) {
        perEmployeeUtil.checkWatchSameDepartmentByEmployeeId(employeeId);
        employeeRepository.findByIdAndIsEnabled(employeeId, true).orElseThrow(
                () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
        );
        List<Family> listFamilyEntity = familyRepository.findAllByEmployeeIdAndIsEnabled(employeeId, true)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new BusinessException(ErrorCode.LIST_FAMILY_IS_EMPTY)
        );
        List<FamilyResponse> listFamilyResponse = new ArrayList<>();
        for (Family familyEntity : listFamilyEntity) {
            listFamilyResponse.add(FamilyMapper.mapToFamilyResponse(familyEntity));
        }
        return listFamilyResponse;
    }

    @Override
    @Transactional
    public void updateFamilyOfEmployee(List<FamilyRequest> requests) {
        perEmployeeUtil.checkUpdateSameDepartmentByEmployeeId(requests.get(0).getEmployeeId());
        if (requests.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_FAMILY_REQUEST_IS_EMPTY);
        }
        List<Family> familyListSave = new ArrayList<>();
        for (FamilyRequest request : requests) {
            Employee employee =  employeeRepository.findByIdAndIsEnabled(request.getEmployeeId(), true).orElseThrow(
                    () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
            );
            getFamilySave(employee, familyListSave, request);
        }
        familyRepository.saveAll(familyListSave);
    }

    @Override
    public void updateFamilyOfEmployee(List<FamilyRequest> requests, Employee employee) {
        if (requests.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_FAMILY_REQUEST_IS_EMPTY);
        }
        List<Family> familyListSave = new ArrayList<>();
        for (FamilyRequest request : requests) {
            getFamilySave(employee, familyListSave, request);
        }
        familyRepository.saveAll(familyListSave);
    }

    private void getFamilySave(Employee employee, List<Family> familyListSave, FamilyRequest request) {
        Family familyEntity = new Family();
        if (request.getId() != null) {
            familyEntity = familyRepository.findByIdAndIsEnabled(request.getId(), true).orElseThrow(
                    () -> new BusinessException(ErrorCode.FAMILY_IS_EMPTY)
            );
        }
        if (Objects.equals(request.getIsUpdate(), UpdateTypeConstant.UPDATE)) {
            checkValidatorRequest(request);
            familyEntity.setEmployeeId(employee.getId());
            familyEntity.setRelationShip(request.getRelationShip());
            familyEntity.setFullName(request.getFullName());
            familyEntity.setDateOfBirth(request.getDateOfBirth());
            familyEntity.setIdentityCard(request.getIdentityCard());
            familyEntity.setIssueDateCCCD(request.getIssueDateCCCD());
            familyEntity.setPlaceCCCD(request.getPlaceCCCD());
            familyEntity.setPhoneNumber(request.getPhoneNumber());
            familyEntity.setDependent(request.isDependent());
            familyEntity.setTaxCode(request.getTaxCode());
            familyListSave.add(familyEntity);
        } else if (Objects.equals(request.getIsUpdate(), UpdateTypeConstant.DELETE)) {
            familyEntity.setEnabled(false);
            familyListSave.add(familyEntity);
        }
    }

    private void checkValidatorRequest(FamilyRequest request) {
        if(request.getRelationShip() == null || request.getRelationShip().isEmpty()) {
            throw new BusinessException(ErrorCode.RELATIONSHIP_ISEMPTY);
        }else if (request.getDateOfBirth() == null || request.getDateOfBirth().toString().isEmpty()) {
            throw new BusinessException(ErrorCode.DATE_OF_BIRTH_IS_EMPTY);
        } else if(request.getFullName() == null || request.getFullName().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        }  else if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            throw new BusinessException(ErrorCode.PHONE_NUMBER_IS_EMPTY);
        } else if (request.getIdentityCard() == null || request.getIdentityCard().isEmpty()) {
            throw new BusinessException(ErrorCode.CCCD_IS_EMPTY);
        } else if (request.getIssueDateCCCD() == null || request.getIssueDateCCCD().toString().isEmpty()) {
            throw new BusinessException(ErrorCode.ISSUE_DATE_CCCD_IS_EMPTY);
        } else if (request.getPlaceCCCD() == null || request.getPlaceCCCD().isEmpty()) {
            throw new BusinessException(ErrorCode.PLACE_CCCD_IS_EMPTY);
        } else if (request.getTaxCode() == null || request.getTaxCode().isEmpty()) {
            throw new BusinessException(ErrorCode.TAX_CODE_IS_EMPTY);
        }
    }
}
