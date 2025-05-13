package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryTableResponse;
import vn.tdsoftware.hrm_backend.entity.SalaryTable;

public class SalaryTableMapper {
    public static SalaryTableResponse mapToSalaryTableResponse(SalaryTable salaryTable) {
        return SalaryTableResponse.builder()
                .yearMonth(salaryTable.getYearMonth())
                .id(salaryTable.getId())
                .nameSalaryTable(salaryTable.getName())
                .status(salaryTable.getStatus())
                .numberEmployee(salaryTable.getNumberEmployee())
                .totalAmount(salaryTable.getTotalAmount())
                .createdAt(salaryTable.getCreatedAt())
                .build();
    }

}
