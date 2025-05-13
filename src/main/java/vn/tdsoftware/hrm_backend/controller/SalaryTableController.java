package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryTableResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.TaxResponse;
import vn.tdsoftware.hrm_backend.service.SalaryTableService;

import java.util.List;

@RestController
@RequestMapping("/salary-table")
@RequiredArgsConstructor
public class SalaryTableController {
    private final SalaryTableService salaryTableService;

    @PostMapping("/get-list-salary-table")
    public ResponseData<List<SalaryTableResponse>> getListSalary() {
        List<SalaryTableResponse> responses = salaryTableService.getListSalaryTable();
        return ResponseData.<List<SalaryTableResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list salary table successfully")
                .build();

    }

    @PostMapping("/get-list-salary-detail")
    public ResponseData<List<SalaryDetailResponse>> getListSalary(@RequestBody EmployeeFilter filter) {
        List<SalaryDetailResponse> responses = salaryTableService.getListSalaryDetail(filter);
        return ResponseData.<List<SalaryDetailResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list salary table successfully")
                .build();
    }

    @PostMapping("/get-count-salary-detail")
    public ResponseData<Integer> getCountSalaryDetail(@RequestBody EmployeeFilter filter) {
        int responses = salaryTableService.getCountSalaryDetail(filter);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(responses)
                .message("Get list salary table successfully")
                .build();
    }

    @PostMapping("/get-list-tax")
    public ResponseData<List<TaxResponse>> getListTax(@RequestBody EmployeeFilter filter) {
        List<TaxResponse> responses = salaryTableService.getListTax(filter);
        return ResponseData.<List<TaxResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list salary table successfully")
                .build();
    }

    @PostMapping("/get-count-tax")
    public ResponseData<Integer> getCountTax(@RequestBody EmployeeFilter filter) {
        int responses = salaryTableService.getCountTax(filter);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(responses)
                .message("Get list salary table successfully")
                .build();
    }


}
