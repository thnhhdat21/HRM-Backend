package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.education.request.EducationRequest;
import vn.tdsoftware.hrm_backend.dto.education.response.EducationResponse;
import vn.tdsoftware.hrm_backend.entity.Employee;

import java.util.List;

public interface EducationService {
    List<EducationResponse> getEducationProfile(long employeeId);
    void updateEducationProfile(List<EducationRequest> requests);
    void updateEducationProfile(List<EducationRequest> requests, Employee employee);

}
