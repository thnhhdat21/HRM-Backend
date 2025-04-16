package vn.tdsoftware.hrm_backend.dto.approvalreason.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApprovalReasonDetail {
    private Long id;
    private String reason;
    private String symbol;
    private Integer maximum;
    private String unit;
    private Boolean workDayEnabled;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime goLate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime backEarly;
    private Integer type;
    private String des;
}
