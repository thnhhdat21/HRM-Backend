package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.family.request.FamilyRequest;
import vn.tdsoftware.hrm_backend.dto.family.resposne.FamilyResponse;
import vn.tdsoftware.hrm_backend.entity.Employee;

import java.util.List;

public interface FamilyService {
    List<FamilyResponse> getFamilyOfEmployee(long employeeId);
    void updateFamilyOfEmployee(List<FamilyRequest> requests);
    void updateFamilyOfEmployee(List<FamilyRequest> requests, Employee employee);

}
