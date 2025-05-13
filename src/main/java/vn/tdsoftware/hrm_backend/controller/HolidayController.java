package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.holiday.request.HolidayFilter;
import vn.tdsoftware.hrm_backend.dto.holiday.request.HolidayRequest;
import vn.tdsoftware.hrm_backend.dto.holiday.response.HolidayResponse;
import vn.tdsoftware.hrm_backend.service.HolidayService;

import java.util.List;


@RestController
@RequestMapping("/holiday")
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayService holidayService;

    @PostMapping("/get-list-holiday")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_TIMESHEET')")
    ResponseData<List<HolidayResponse>> getListHoliday(@RequestBody HolidayFilter filter) {
        List<HolidayResponse> responses = holidayService.getListHoliday(filter);
        return ResponseData.<List<HolidayResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list holiday")
                .build();
    }


    @PostMapping("/update-holiday")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_TIMESHEET')")
    ResponseData<HolidayResponse> updateHoliday(@RequestBody HolidayRequest request) {
        HolidayResponse responses = holidayService.updateHoliday(request);
        return ResponseData.<HolidayResponse>builder()
                .code(1000)
                .data(responses)
                .message("Update holiday successfully")
                .build();

    }

    @PostMapping("/delete-holiday")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_TIMESHEET')")
    ResponseData<Void> updateHoliday(@RequestParam long holidayId) {
        holidayService.deleteHoliday(holidayId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update holiday successfully")
                .build();

    }

}
