package vn.tdsoftware.hrm_backend.dto.letterreason.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class LetterReasonResponse {
    private Long id;
    private String reason;
    private Integer maximum;
    private Boolean workDayEnabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    private String des;
    private String status;
}
