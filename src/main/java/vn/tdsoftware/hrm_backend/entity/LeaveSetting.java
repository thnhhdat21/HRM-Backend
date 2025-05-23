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
@Table(name = "LeaveSetting")
public class LeaveSetting extends BaseEntity<Integer> implements Serializable {
    @Column(name = "annualLeaveDays")
    private Integer annualLeaveDays;

    @Column(name = "seniorLeaveEnabled")
    private Boolean seniorLeaveEnabled;

    @Column(name = "seniorYears")
    private Integer seniorYears;
}
