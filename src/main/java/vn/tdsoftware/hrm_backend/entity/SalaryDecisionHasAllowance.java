package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.checkerframework.checker.units.qual.A;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SalaryDecisionHasAllowance")
public class SalaryDecisionHasAllowance extends BaseEntity<Long> implements Serializable {
    @Column(name = "salaryDecisionId")
    private long salaryDecisionId;

    @Column(name = "allowanceId")
    private long allowanceId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "unit")
    private String unit;
}
