package vn.tdsoftware.hrm_backend.dto.family.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRequest {
    private Long id;
    private Long employeeId;
    private String fullName;
    private String relationShip;
    private Date dateOfBirth;
    private String identityCard;
    private Date issueDateCCCD;
    private String placeCCCD;
    private String phoneNumber;
    private boolean dependent;
    private String taxCode;
    private String isUpdate;
}
