package vn.tdsoftware.hrm_backend.dto.contract.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
public class ContractResponse {
    private long contractId;
    private String createdBy;
    private String contractCode;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String contractName;
    private String contractStatus;
    private int contractState;
    private Date contractDateLiquid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private Date dateSign;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private Date dateEnd;
}
