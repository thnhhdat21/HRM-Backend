package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.department.request.DepartmentRequest;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentDetailResponse;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentResponse;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentTreeResponse;
import vn.tdsoftware.hrm_backend.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/get-list-department-child")
    public ResponseData<List<DepartmentResponse>> getListDepartmentChild(){
        List<DepartmentResponse> response = departmentService.getListDepartmentChild();
        return ResponseData.<List<DepartmentResponse>>builder()
                .code(1000)
                .data(response)
                .build();
    }
}
