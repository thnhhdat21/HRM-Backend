package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobPosition")
public class JobPosition extends BaseEntity<Long> implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "dutyId")
    private Long dutyId;

    @Column(name = "roleId")
    private int roleId;

    @Column(name = "salaryFrom")
    private int salaryFrom;

    @Column(name = "salaryTo")
    private int salaryTo;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;
}
