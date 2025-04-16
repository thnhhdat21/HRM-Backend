package vn.tdsoftware.hrm_backend.dto.insuranceratio.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class InsuranceRatioRequest {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Ho_Chi_Minh")
    private Date dateStart;
    private List<RatioDetailRequest> ratios;
}
