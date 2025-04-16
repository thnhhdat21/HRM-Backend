package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.duty.request.DutyRequest;
import vn.tdsoftware.hrm_backend.dto.duty.response.DutyResponse;

import java.util.List;

public interface DutyService {
    List<DutyResponse> getListDuty();
    List<DutyResponse> createDuty(List<DutyRequest> dutyRequest);
    DutyResponse updateDuty(Long id, String name, String des);
    void deleteDuty(long id);
}
