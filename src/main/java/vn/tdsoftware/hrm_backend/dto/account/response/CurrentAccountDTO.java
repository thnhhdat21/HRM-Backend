package vn.tdsoftware.hrm_backend.dto.account.response;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CurrentAccountDTO {
    @Getter
    private static Long employeeId;

    @Getter
    private static Long departmentId;

    @Getter
    private static Set<String> permission;

    public static void create(Long employeeId, Long departmentId, List<String> permissionList) {
        CurrentAccountDTO.employeeId = employeeId;
        CurrentAccountDTO.departmentId = departmentId;
        CurrentAccountDTO.permission = new HashSet<>(permissionList);
    }
}
