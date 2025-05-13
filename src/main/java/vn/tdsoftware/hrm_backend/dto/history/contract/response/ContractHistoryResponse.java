package vn.tdsoftware.hrm_backend.dto.history.contract.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ContractHistoryResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy ", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    private String createdBy;
    private Integer state;
    private String title;
}
