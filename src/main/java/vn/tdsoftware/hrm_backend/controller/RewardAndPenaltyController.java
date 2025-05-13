package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response.RewardAndPenaltyResponse;
import vn.tdsoftware.hrm_backend.service.RewardAndPenaltyService;

import java.util.List;

@RestController
@RequestMapping("/reward-penalty")
@RequiredArgsConstructor
public class RewardAndPenaltyController {
    private final RewardAndPenaltyService rewardAndPenaltyService;

    @PostMapping("/get-list")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_EMPLOYEE'," +
                                    "'ROLE_MANAGE_DECISION', " +
                                    "'ADMIN')")
    public ResponseData<List<RewardAndPenaltyResponse>> getList(@RequestParam("type") int type) {
        List<RewardAndPenaltyResponse> response = rewardAndPenaltyService.getList(type);
        return ResponseData.<List<RewardAndPenaltyResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get list successfully")
                .build();
    }
}
