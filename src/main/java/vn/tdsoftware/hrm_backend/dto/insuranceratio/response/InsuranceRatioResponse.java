package vn.tdsoftware.hrm_backend.dto.insuranceratio.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class InsuranceRatioResponse {
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date dateStart;
}
