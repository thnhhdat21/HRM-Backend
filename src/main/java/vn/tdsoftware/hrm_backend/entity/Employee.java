package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

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
    private LocalDate dateOfBirth;

    @Column(name = "marriageStatus")
    private Boolean marriageStatus;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "nation")
    private String nation;

    @Column(name = "religion")
    private String religion;

    @Column(name = "ethnic")
    private String ethnic;

    @Column(name = "identityCard")
    private String identityCard;

    @Column(name = "placeCCCD")
    private String placeCCCD;

    @Column(name = "issueDateCCCD")
    private LocalDate issueDateCCCD;

    @Column(name = "placeOfBirth")
    private String placeOfBirth;

    @Column(name = "taxCode")
    private String taxCode;

    @Column(name = "fontImageCCCD")
    private String fontImageCCCD;

    @Column(name = "backImageCCCD")
    private String backImageCCCD;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "dateJoin")
    private LocalDate dateJoin;

    @Column(name = "insuranceNumber")
    private String insuranceNumber;

    @Column(name = "insuranceCard")
    private String insuranceCard;

//    @Column(name = "departmentId")
//    private Long departmentId;
//
//    @Column(name = "jobPositionId")
//    private Long jobPositionId;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Employee employee = (Employee) o;
//        return Objects.equals(employeeCode, employee.employeeCode); // Hoặc bạn có thể so sánh theo mã nhân viên (employeeCode)
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(employeeCode); // Hoặc employeeCode nếu bạn muốn
//    }
}
