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
@Table(name = "InsuranceSetting")
public class InsuranceSetting extends BaseEntity<Integer> implements Serializable {

    @Column(name = "closingDateIncrease")
    private Integer closingDateIncrease;

    @Column(name = "singedContract")
    private Boolean singedContract;

    @Column(name = "returnedFromMaternity")
    private Boolean returnedFromMaternity;

    @Column(name = "returnedFromUnpaidLeave")
    private Boolean returnedFromUnpaidLeave;

    @Column(name = "increasedContribution")
    private Boolean increasedContribution;

    @Column(name = "closingDateDecrease")
    private Integer closingDateDecrease;

    @Column(name = "contractTerminated")
    private Boolean contractTerminated;

    @Column(name = "maternityLeave")
    private Boolean maternityLeave;

    @Column(name = "decreasedContribution")
    private Boolean decreasedContribution;

    @Column(name = "unpaidLeave")
    private Boolean unpaidLeave;

    @Column(name = "maxUnpaidLeaveDay")
    private Integer maxUnpaidLeaveDay;
}
