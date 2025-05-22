package vn.tdsoftware.hrm_backend.dto.employee.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ResumeProfileResponse {
    private long id;
    private String avatar;
    private String fullName;
    private String employeeCode;
    private Date dateOfBirth;
    private String gender;
    private int type;
    private String marriageStatus;
    private String nation;
    private String phoneNumber;
    private String email;
    private String religion; // tôn giáo
    private String ethnic; // dân tộc
    private String identityCard;
    private Date issueDateCCCD;
    private String placeCCCD;
    private String placeOfBirth;
    private String homeTown; // nguyên quán
    private String permanentAddress; // thường trú
    private String currentAddress;// chỗ ở hiện tại
    private String taxCode;
    private String accountBank;
    private String fontImageCCCD;
    private String backImageCCCD;
    private String bankName;
    private String bankAccountName;
}
