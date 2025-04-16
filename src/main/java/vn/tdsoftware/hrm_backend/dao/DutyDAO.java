package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.duty.response.DutyResponse;

import java.util.List;

@Repository
public interface DutyDAO {
    List<DutyResponse> getListDuty();
    void deleteDuty(long id);
}
