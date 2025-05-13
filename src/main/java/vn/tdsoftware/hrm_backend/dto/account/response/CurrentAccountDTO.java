package vn.tdsoftware.hrm_backend.dto.account.response;

import lombok.Getter;

public class CurrentAccountDTO {
    @Getter
    private static Long employeeId;

    public static void create(Long employeeId) {
        CurrentAccountDTO.employeeId = employeeId;
    }
}
