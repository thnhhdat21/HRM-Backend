package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SalaryDecision")
public class SalaryDecision extends BaseEntity<Long> implements Serializable {
    @Column(name = "decisionId")
    private long decisionId;

    @Column(name = "dateActive")
    private LocalDate dateActive;

    @Column(name = "amountOld")
    private int amountOld;

    @Column(name = "amountNew")
    private int amountNew;
}
