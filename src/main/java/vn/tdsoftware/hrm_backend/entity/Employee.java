package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee extends BaseEntity<Long> implements Serializable {

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "employeeCode")
    private String employeeCode;

    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @Column(name = "marriageStatus")
    private String marriageStatus;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "nation")
    private String nation;

    @Column(name = "religion")
    private String religion;

    @Column(name = "cCCD")
    private String cCCD;

    @Column(name = "placeCCCD")
    private String placeCCCD;

    @Column(name = "placeOfBirth")
    private String placeOfBirth;

    @Column(name = "taxCode")
    private String taxCode;

    @Column(name = "fontImageCCCD")
    private String fontImageCCCD;

    @Column(name = "backImageCCCD")
    private String backImageCCCD;

    @Column(name = "departmentId")
    private Long departmentId;

    @Column(name = "jobPositionId")
    private Long jobPositionId;
}
