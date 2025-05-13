package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.HolidayDAO;
import vn.tdsoftware.hrm_backend.dto.holiday.request.HolidayFilter;
import vn.tdsoftware.hrm_backend.dto.holiday.request.HolidayRequest;
import vn.tdsoftware.hrm_backend.dto.holiday.response.HolidayResponse;
import vn.tdsoftware.hrm_backend.entity.Holiday;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.HolidayMapper;
import vn.tdsoftware.hrm_backend.repository.HolidayRepository;
import vn.tdsoftware.hrm_backend.service.HolidayService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {
    private final HolidayRepository holidayRepository;
    private final HolidayDAO holidayDAO;

    @Override
    public List<HolidayResponse> getListHoliday(HolidayFilter filter) {
        List<HolidayResponse> response = holidayDAO.getListHoliday(filter.getYear(), filter.getPageIndex(), filter.getType());
        if(response.isEmpty()) {
            throw new BusinessException(ErrorCode.HOLIDAY_IS_EMPTY);
        }
        return response;
    }

    @Override
    public HolidayResponse updateHoliday(HolidayRequest holidayRequest) {
        Holiday holidayEntity = holidayRepository.findByIdAndIsEnabled(holidayRequest.getId(), true)
                .orElse(new Holiday());
        holidayEntity.setReason(holidayRequest.getReason());
        holidayEntity.setType(holidayRequest.getType());
        holidayEntity.setDate(holidayRequest.getDate());
        holidayEntity.setDescription(holidayRequest.getDescription());
        Holiday holidaySaved = holidayRepository.save(holidayEntity);
        return HolidayMapper.mapToHolidayResponse(holidaySaved);
    }

    @Override
    public void deleteHoliday(long holidayId) {
        Holiday holidayEntity = holidayRepository.findByIdAndIsEnabled(holidayId, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.HOLIDAY_IS_EMPTY));

        holidayEntity.setEnabled(false);
        holidayRepository.save(holidayEntity);
    }
}
