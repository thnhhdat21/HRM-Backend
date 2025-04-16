package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftDetail;

@Repository
public interface WorkShiftDAO {
    void deleteWorkShift(long id);
    WorkShiftDetail getDetailWorkShift(long id);
}
