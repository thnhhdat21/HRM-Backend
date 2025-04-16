package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionDetailResponse;
import vn.tdsoftware.hrm_backend.dto.jobposition.response.JobPositionResponse;
import vn.tdsoftware.hrm_backend.entity.JobPosition;

public class JobPositionMapper {

    public static JobPositionResponse mapToJobPositionResponse(JobPosition jobPosition) {
        return JobPositionResponse.builder()
                .id(jobPosition.getId())
                .name(jobPosition.getName())
                .salaryFrom(jobPosition.getSalaryFrom())
                .salaryTo(jobPosition.getSalaryTo())
                .status(jobPosition.getStatus())
                .des(jobPosition.getDescription())
                .build();
    }

    public static JobPositionDetailResponse mapToJobPositionDetailResponse(JobPosition jobPosition) {
        return JobPositionDetailResponse.builder()
                .id(jobPosition.getId())
                .name(jobPosition.getName())
                .dutyId(jobPosition.getDutyId())
                .roleId(jobPosition.getRoleId())
                .salaryFrom(jobPosition.getSalaryFrom())
                .salaryTo(jobPosition.getSalaryTo())
                .des(jobPosition.getDescription())
                .build();
    }
}
