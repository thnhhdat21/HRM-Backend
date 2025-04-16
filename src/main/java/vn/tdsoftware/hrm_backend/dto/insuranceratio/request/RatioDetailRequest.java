package vn.tdsoftware.hrm_backend.dto.insuranceratio.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatioDetailRequest {
    private long detailId;
    private int type;
    private double ratio;
    private double companyPay;
    private boolean hasUpdate;
}
