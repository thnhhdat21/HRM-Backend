package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.AutoCheckoutDAO;
import vn.tdsoftware.hrm_backend.entity.AutoCheckout;

@Component
public class AutoCheckoutDAOImpl extends AbstractDao<AutoCheckout> implements AutoCheckoutDAO {

    @Override
    public void deleteAutoCheckout(long workShiftId) {
        String sql = "update AutoCheckout " +
                "set isEnabled = 0 " +
                "where workShiftId = ?";
        update(sql, workShiftId);
    }
}
