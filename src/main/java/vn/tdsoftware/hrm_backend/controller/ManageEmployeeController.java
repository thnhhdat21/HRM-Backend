package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeOfDepartment;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeTypeCount;
import vn.tdsoftware.hrm_backend.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/manage-employee")
@RequiredArgsConstructor
public class ManageEmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/get-list-employee")
    public ResponseData<List<EmployeeOfDepartment>> getListEmployeeFilter(@RequestBody EmployeeFilter request) {
        System.out.println(employeeService.getCountEmployeeFilter(request));
        List<EmployeeOfDepartment> responses= employeeService.getListEmployeeFilter(request);
        return ResponseData.<List<EmployeeOfDepartment>>builder()
                .code(1000)
                .data(responses)
                .message("Get List employee successfully")
                .build();
    }

    @PostMapping("/get-count-employee-type")
    public ResponseData<List<EmployeeTypeCount>> getCountEmployeeFilter(@RequestBody EmployeeFilter request) {
        List<EmployeeTypeCount> responses= employeeService.getCountEmployeeFilter(request);
        return ResponseData.<List<EmployeeTypeCount>>builder()
                .code(1000)
                .data(responses)
                .message("Get List employee successfully")
                .build();
    }



}
