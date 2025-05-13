package vn.tdsoftware.hrm_backend.dto.employee.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.tdsoftware.hrm_backend.dto.contract.request.ContractRequest;
import vn.tdsoftware.hrm_backend.dto.education.request.EducationRequest;
import vn.tdsoftware.hrm_backend.dto.family.request.FamilyRequest;

import java.util.List;

@Getter
@Setter
@ToString
public class FullEmployeeRequest {
    private ResumeRequest resumeRequest;
    private List<EducationRequest> educationRequest;
    private List<FamilyRequest> familyRequest;
    private ContractRequest contractRequest;
    private InsuranceEmployeeRequest insuranceRequest;
}
