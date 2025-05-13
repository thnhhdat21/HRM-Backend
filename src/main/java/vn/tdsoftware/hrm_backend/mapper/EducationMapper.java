package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.education.response.EducationResponse;
import vn.tdsoftware.hrm_backend.entity.Education;

public class EducationMapper {

    public static EducationResponse mapToEducationResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .toMonth(education.getToMonth())
                .fromMonth(education.getFromMonth())
                .level(education.getLevel())
                .placeTraining(education.getPlaceTraining())
                .major(education.getMajor())
                .methodTraining(education.getMethodTraining())
                .build();
    }
}
