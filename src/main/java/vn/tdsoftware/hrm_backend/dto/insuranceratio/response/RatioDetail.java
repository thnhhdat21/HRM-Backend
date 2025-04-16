package vn.tdsoftware.hrm_backend.dto.insuranceratio.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RatioDetail {
    private long detailId;
    private int type;
    private double ratio;
    private double companyPay;
}
