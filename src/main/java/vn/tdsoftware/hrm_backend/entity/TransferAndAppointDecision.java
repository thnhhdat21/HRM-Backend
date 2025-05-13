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
@Table(name = "TransferAndAppointDecision")
public class TransferAndAppointDecision extends BaseEntity<Long> implements Serializable {
    @Column(name = "decisionId")
    private Long decisionId;

    @Column(name = "departmentOld")
    private Long departmentOld;

    @Column(name = "jobPositionOld")
    private Long jobPositionOld;

    @Column(name = "departmentNew")
    private Long departmentNew;

    @Column(name = "jobPositionNew")
    private Long jobPositionNew;
}
