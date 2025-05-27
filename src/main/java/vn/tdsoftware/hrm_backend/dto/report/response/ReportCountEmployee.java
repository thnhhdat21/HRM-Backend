package vn.tdsoftware.hrm_backend.dto.report.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportCountEmployee {
    private String department;
    private int count;
}
