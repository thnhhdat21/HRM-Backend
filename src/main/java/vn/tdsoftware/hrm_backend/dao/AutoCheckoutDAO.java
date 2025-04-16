package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface AutoCheckoutDAO {
    void deleteAutoCheckout(long workShiftId);
}
