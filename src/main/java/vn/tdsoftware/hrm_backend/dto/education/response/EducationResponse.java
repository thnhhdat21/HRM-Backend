package vn.tdsoftware.hrm_backend.dto.education.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EducationResponse {
    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate toMonth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate fromMonth;
    private String level;
    private String placeTraining;
    private String major;
    private String methodTraining;
}
