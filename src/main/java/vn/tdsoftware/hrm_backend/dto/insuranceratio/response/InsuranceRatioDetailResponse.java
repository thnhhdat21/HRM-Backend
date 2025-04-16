package vn.tdsoftware.hrm_backend.dto.insuranceratio.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class InsuranceRatioDetailResponse {
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Ho_Chi_Minh")
    private Date dateStart;
    private List<RatioDetail> ratios;
}
