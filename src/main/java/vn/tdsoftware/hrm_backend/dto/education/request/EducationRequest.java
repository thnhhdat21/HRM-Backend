package vn.tdsoftware.hrm_backend.dto.education.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class EducationResponse {
    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date toMonth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date fromMonth;
    private String level;
    private String placeTraining;
    private String major;
    private String methodTraining;
}
