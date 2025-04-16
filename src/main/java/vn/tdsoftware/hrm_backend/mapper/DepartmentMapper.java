package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentDetailResponse;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentResponse;
import vn.tdsoftware.hrm_backend.entity.Department;

public class DepartmentMapper {

    public static DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .level(department.getDepartmentLevel())
                .build();
    }

    public static DepartmentDetailResponse mapToDepartmentDetailResponse(Department department) {
        return DepartmentDetailResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .departmentLevel(department.getDepartmentLevel())
                .businessBlockId(department.getBusinessBlockId())
                .parentId(department.getParentId())
                .build();
    }
}
