package vn.tdsoftware.hrm_backend.dto.employee.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeSelect {
    private long id;
    private String name;
    private String code;
}
