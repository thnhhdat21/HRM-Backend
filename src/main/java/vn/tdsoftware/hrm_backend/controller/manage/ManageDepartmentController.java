package vn.tdsoftware.hrm_backend.controller.manage;

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
@RequestMapping("/admin/department")
@RequiredArgsConstructor
public class ManageDepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/get-list-department")
    public ResponseData<List<DepartmentTreeResponse>> getListDepartment(){
        List<DepartmentTreeResponse> response = departmentService.getAllDepartments();
        return ResponseData.<List<DepartmentTreeResponse>>builder()
                .code(1000)
                .data(response)
                .build();
    }

    @PostMapping("/create-department")
    public ResponseData<DepartmentResponse> createDepartment(@RequestBody DepartmentRequest request){
        DepartmentResponse response = departmentService.createDepartment(request);
        return ResponseData.<DepartmentResponse>builder()
                .code(1000)
                .data(response)
                .message("Create department successful")
                .build();
    }

    @PostMapping("/update-department")
    public ResponseData<DepartmentResponse> updateDepartment(@RequestBody DepartmentRequest request){
        System.out.println("business: " + request.getBusinessBlockId());
        DepartmentResponse response = departmentService.updateDepartment(request);
        return ResponseData.<DepartmentResponse>builder()
                .code(1000)
                .data(response)
                .message("Update department successful")
                .build();
    }

    @PostMapping("/delete-department")
    public ResponseData<Void> deleteDepartment(@RequestParam("id") Long id){
        departmentService.deleteDepartment(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("delete department successful")
                .build();
    }

    @PostMapping("/get-department-detail")
    public ResponseData<DepartmentDetailResponse> getDepartmentDetail(@RequestParam("id") Long id){
        DepartmentDetailResponse response = departmentService.getDepartmentDetail(id);
        return ResponseData.<DepartmentDetailResponse>builder()
                .code(1000)
                .data(response)
                .message("delete department successful")
                .build();
    }


}
