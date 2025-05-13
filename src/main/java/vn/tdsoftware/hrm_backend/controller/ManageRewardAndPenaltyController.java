package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.request.RewardAndPenaltyRequest;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response.RewardAndPenaltyResponse;
import vn.tdsoftware.hrm_backend.service.RewardAndPenaltyService;

import java.util.List;

@RestController
@RequestMapping("/admin/reward-penalty")
@RequiredArgsConstructor
public class ManageRewardAndPenaltyController {
    private final RewardAndPenaltyService rewardAndPenaltyService;

    @PostMapping("/create-new")
    public ResponseData<List<RewardAndPenaltyResponse>> getList(@RequestBody List<RewardAndPenaltyRequest> request) {
        List<RewardAndPenaltyResponse> response = rewardAndPenaltyService.createNew(request);
        return ResponseData.<List<RewardAndPenaltyResponse>>builder()
                .code(1000)
                .data(response)
                .message("Create successfully")
                .build();
    }

    @PostMapping("/update")
    public ResponseData<RewardAndPenaltyResponse> update(@RequestBody RewardAndPenaltyRequest request) {
        RewardAndPenaltyResponse response = rewardAndPenaltyService.update(request);
        return ResponseData.<RewardAndPenaltyResponse>builder()
                .code(1000)
                .data(response)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/delete")
    public ResponseData<Void> delete(@RequestParam long id) {
       rewardAndPenaltyService.delete(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete successfully")
                .build();
    }
}
