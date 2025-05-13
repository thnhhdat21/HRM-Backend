package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.department.request.DepartmentRequest;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentDetailResponse;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentResponse;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentTreeResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentTreeResponse> getAllDepartments();
    DepartmentResponse createDepartment(DepartmentRequest departmentRequest);
    DepartmentResponse updateDepartment(DepartmentRequest departmentRequest);
    void deleteDepartment(Long id);
    DepartmentDetailResponse getDepartmentDetail(Long id);
    List<DepartmentResponse> getListDepartmentChild();
}
