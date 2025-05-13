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
@Table
public class OnLeave extends BaseEntity<Long> implements Serializable {
    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "seniorDay")
    private Double seniorDay;

    @Column(name = "regulaDay")
    private Double regulaDay;

    @Column(name = "usedDay")
    private Double usedDay;

    @Column(name = "year")
    private Integer year;
}
