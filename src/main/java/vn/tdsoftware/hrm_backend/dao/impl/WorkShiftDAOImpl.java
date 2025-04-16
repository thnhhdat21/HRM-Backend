package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.WorkShiftDAO;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftDetail;
import vn.tdsoftware.hrm_backend.entity.WorkShift;
import vn.tdsoftware.hrm_backend.mapper.response.workshift.WorkShiftDetailMapper;

import java.util.List;

@Component
public class WorkShiftDAOImpl extends AbstractDao<WorkShift> implements WorkShiftDAO {
    @Override
    public void deleteWorkShift(long id) {
        String sql = "update WorkShift " +
                "set isEnabled = 0 " +
                "where id = ?";
        update(sql, id);
    }

    @Override
    public WorkShiftDetail getDetailWorkShift(long id) {
        String sql = "select workShift.id, " +
                "workShift.code, " +
                "workShift.name, " +
                "workShift.timeIn , " +
                "workShift.timeOut , " +
                "workShift.nextDayEnabled, " +
                "workShift.breakStartTime , " +
                "workShift.breakEndTime , " +
                "workShift.totalTime , " +
                "workShift.totalWorkDay , " +
                "workShift.checkInFirst , " +
                "workShift.checkoutLater , " +
                "workShift.autoTimekeeping , " +
                "workShift.autoCheckoutForPosition , " +
                "GROUP_CONCAT(autoCheckout.JobPositionId) AS jobPositions " +
                "from workShift left join autoCheckout on workShift.id = autoCheckout.workShiftId and autoCheckout.isEnabled = true " +
                "where workShift.isEnabled = true and workShift.id = ? " +
                "group by workShift.id ";
        List<WorkShiftDetail> responses = query(sql, new WorkShiftDetailMapper(), id);
        return responses.get(0);
    }
}
