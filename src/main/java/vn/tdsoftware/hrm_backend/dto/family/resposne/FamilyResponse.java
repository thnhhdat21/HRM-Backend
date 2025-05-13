package vn.tdsoftware.hrm_backend.dto.family.resposne;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class FamilyResponse {
    private Long id;
    private String fullName;
    private String relationShip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private Date dateOfBirth;
    private String identityCard;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private Date issueDateCCCD;
    private String placeCCCD;
    private String phoneNumber;
    private Boolean dependent;
    private String taxCode;
}
