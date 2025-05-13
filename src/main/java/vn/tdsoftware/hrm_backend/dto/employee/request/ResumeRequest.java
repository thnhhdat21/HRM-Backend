package vn.tdsoftware.hrm_backend.dto.employee.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ResumeRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private boolean gender;
    private boolean marriageStatus;
    private String nation;
    private String phoneNumber;
    private String religion; // tôn giáo
    private String ethnic; // dân tộc
    private String identityCard;
    private LocalDate issueDateCCCD;
    private String placeCCCD;
    private String placeOfBirth;
    private String homeTown; // nguyên quán
    private String permanentAddress; // thường trú
    private String currentAddress;// chỗ ở hiện tại
    private String taxCode;
    private String bankAccountName;
    private String accountBank;
    private String bankName;
    private String email;
    private LocalDate dateJoin;
}
