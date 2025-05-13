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
@Table(name = "RewardAndPenaltyDecision")
public class RewardAndPenaltyDecision extends BaseEntity<Long> implements Serializable {

    @Column(name = "decisionId")
    private Long decisionId;

    @Column(name = "rewardAndPenaltyId")
    private Long rewardAndPenaltyId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "unit")
    private String unit;
}
