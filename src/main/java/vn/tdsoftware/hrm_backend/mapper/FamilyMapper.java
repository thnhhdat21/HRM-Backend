package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.family.resposne.FamilyResponse;
import vn.tdsoftware.hrm_backend.entity.Family;

public class FamilyMapper {

    public static FamilyResponse mapToFamilyResponse(Family family) {
        return FamilyResponse.builder()
                .id(family.getId())
                .fullName(family.getFullName())
                .relationShip(family.getRelationShip())
                .dateOfBirth(family.getDateOfBirth())
                .identityCard(family.getIdentityCard())
                .issueDateCCCD(family.getIssueDateCCCD())
                .placeCCCD(family.getPlaceCCCD())
                .phoneNumber(family.getPhoneNumber())
                .dependent(family.getDependent())
                .taxCode(family.getTaxCode())
                .build();
    }
}
