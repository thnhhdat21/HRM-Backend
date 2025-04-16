package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.workshift.request.AutoCheckoutRequest;
import vn.tdsoftware.hrm_backend.dto.workshift.request.WorkShiftRequest;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftDetail;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftResponse;

import java.util.List;

public interface WorkShiftService {
    List<WorkShiftResponse> getList();
    WorkShiftResponse createWorkShift(WorkShiftRequest workShiftRequest);
    WorkShiftResponse updateWorkShift(WorkShiftRequest workShiftRequest);
    void updateAutoCheckout(AutoCheckoutRequest autoCheckoutRequests);
    void deleteWorkShift(long id);
    WorkShiftDetail getDetailWorkShift(long id);
}
