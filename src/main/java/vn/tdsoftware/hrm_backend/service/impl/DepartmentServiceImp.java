package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.DepartmentDAO;
import vn.tdsoftware.hrm_backend.dto.department.request.DepartmentRequest;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentDetailResponse;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentResponse;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentTreeResponse;
import vn.tdsoftware.hrm_backend.entity.Department;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.DepartmentMapper;
import vn.tdsoftware.hrm_backend.repository.DepartmentRepository;
import vn.tdsoftware.hrm_backend.service.DepartmentService;
import vn.tdsoftware.hrm_backend.util.FieldStringUtil;
import vn.tdsoftware.hrm_backend.util.constant.DepartmentLevelConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {
    private final DepartmentDAO departmentDAO;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentTreeResponse> getAllDepartments() {
        List<DepartmentTreeResponse> listResponse = departmentDAO.findAll();
        return buildTree(listResponse);
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        FieldStringUtil.validatorNameAndCode(departmentRequest.getName(), departmentRequest.getCode());
        Department departmentSaved = departmentRepository.save(
                Department.builder()
                        .name(departmentRequest.getName())
                        .code(departmentRequest.getCode())
                        .departmentLevel(departmentRequest.getLevel())
                        .parentId(departmentRequest.getParentId())
                        .businessBlockId(departmentRequest.getBusinessBlockId())
                        .build()
        );
        return DepartmentMapper.mapToDepartmentResponse(departmentSaved);
    }

    @Override
    public DepartmentResponse updateDepartment(DepartmentRequest departmentRequest) {
        FieldStringUtil.validatorNameAndCode(departmentRequest.getName(), departmentRequest.getCode());
        Department departmentSaved = departmentRepository.findByIdAndIsEnabled(departmentRequest.getId(), true);
        if (departmentSaved == null) {
            throw new BusinessException(ErrorCode.DEPARTMENT_IS_EMPTY);
        }
        departmentSaved.setName(departmentRequest.getName());
        departmentSaved.setCode(departmentRequest.getCode());
        departmentSaved.setDepartmentLevel(departmentRequest.getLevel());
        departmentSaved.setParentId(departmentRequest.getParentId());
        departmentSaved.setBusinessBlockId(departmentRequest.getBusinessBlockId());
        return DepartmentMapper.mapToDepartmentResponse(departmentRepository.save(departmentSaved));
    }

    @Override
    public void deleteDepartment(Long id) {
        int count = departmentDAO.countEmployeeInDepartment(id);
        if (count > 0) {
            throw new BusinessException(ErrorCode.DELETE_DEPARTMENT_ERROR);
        }
        departmentDAO.deleteDepartment(id);
    }

    @Override
    public DepartmentDetailResponse getDepartmentDetail(Long id) {
        Department departmentSaved = departmentRepository.findByIdAndIsEnabled(id, true);
        if (departmentSaved == null) {
            throw new BusinessException(ErrorCode.DEPARTMENT_IS_EMPTY);
        }
        return DepartmentMapper.mapToDepartmentDetailResponse(departmentSaved);
    }

    @Override
    public List<DepartmentResponse> getListDepartmentChild() {
        Integer[] levels = new Integer[] {
                DepartmentLevelConstant.DEPARTMENT,
                DepartmentLevelConstant.BGD
        };
        List<Department> departmentList = departmentRepository.findAllByDepartmentLevelInAndIsEnabled(levels, true).orElseThrow(
                () -> new BusinessException(ErrorCode.DEPARTMENT_IS_EMPTY)
        );
        List<DepartmentResponse> listResponse = new ArrayList<>();
        for (Department department : departmentList) {
            listResponse.add(DepartmentMapper.mapToDepartmentResponse(department));
        }
        return listResponse;
    }

    @Override
    public Long getDepartmentByEmployeeId(long employeeId) {
        Long departmentId = departmentDAO.getDepartmentByEmployeeId(employeeId);
        if (departmentId == null) {
            throw new BusinessException(ErrorCode.DEPARTMENT_IS_EMPTY);
        }
        return departmentId;

    }

    private List<DepartmentTreeResponse> buildTree(List<DepartmentTreeResponse> list) {
        HashMap<Long, DepartmentTreeResponse> map = new HashMap<>();
        List<DepartmentTreeResponse> listResponse = new ArrayList<>();
        for (DepartmentTreeResponse departmentTree : list) {
            if(departmentTree.getParentId() == 0) {
                listResponse.add(departmentTree);
            }
            map.put(departmentTree.getId(), departmentTree);
        }

        for (DepartmentTreeResponse department : list) {
            if(department.getParentId() == 0) {
                continue;
            }
            DepartmentTreeResponse parent = map.get(department.getParentId());
            if(parent.getChildren() == null) {
                List<DepartmentTreeResponse> children = new ArrayList<>();
                children.add(department);
                parent.setChildren(children);
            }else {
                parent.getChildren().add(department);
            }
        }
        return listResponse;
    }
}
