package vn.tdsoftware.hrm_backend.mapper;

import org.springframework.web.multipart.MultipartFile;
import vn.tdsoftware.hrm_backend.dto.employee.request.ResumeProfileRequest;
import vn.tdsoftware.hrm_backend.dto.employee.request.ResumeRequest;
public class ResumeMapper {

    public static ResumeProfileRequest mapToResumeProfileRequest(ResumeRequest request, MultipartFile file) {
            return ResumeProfileRequest.builder()
                        .fullName(request.getFullName())
                        .dateOfBirth(request.getDateOfBirth())
                        .gender(request.isGender())
                        .marriageStatus(request.isMarriageStatus())
                        .nation(request.getNation())
                        .phoneNumber(request.getPhoneNumber())
                        .religion(request.getReligion()) // tôn giáo
                        .ethnic(request.getEthnic()) // dân tộc
                        .identityCard(request.getIdentityCard())
                        .issueDateCCCD(request.getIssueDateCCCD())
                        .placeCCCD(request.getPlaceCCCD())
                        .placeOfBirth(request.getPlaceOfBirth())
                        .homeTown(request.getHomeTown()) // nguyên quán
                        .permanentAddress(request.getPermanentAddress()) // thường trú
                        .currentAddress(request.getCurrentAddress())// chỗ ở hiện tại
                        .taxCode(request.getTaxCode())
                        .bankAccountName(request.getBankAccountName())
                        .accountBank(request.getAccountBank())
                        .bankName(request.getBankName())
                        .email(request.getEmail())
                        .dateJoin(request.getDateJoin())
                        .avatar(file)
                        .build();
    }
}
