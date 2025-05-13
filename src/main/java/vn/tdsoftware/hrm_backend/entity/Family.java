package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "family")
public class Family extends BaseEntity<Long> implements Serializable {
    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "relationShip")
    private String relationShip;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "identityCard")
    private String identityCard;

    @Column(name = "placeCCCD")
    private String placeCCCD;

    @Column(name = "issueDateCCCD")
    private Date issueDateCCCD;

    @Column(name = "placeOfBirth")
    private String placeOfBirth;

    @Column(name = "taxCode")
    private String taxCode;

    @Column(name = "dependent")
    private Boolean dependent;
}
