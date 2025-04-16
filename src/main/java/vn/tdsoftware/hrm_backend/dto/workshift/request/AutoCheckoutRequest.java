package vn.tdsoftware.hrm_backend.dto.workshift.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AutoCheckoutRequest {
    private Long id;
    private List<Long> jobPositionId;
}
