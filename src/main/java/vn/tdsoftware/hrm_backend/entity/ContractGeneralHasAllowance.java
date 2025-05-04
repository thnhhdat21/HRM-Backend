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
@Table(name = "ContractGeneralHasAllowance")
public class ContractGeneralHasAllowance extends BaseEntity<Long> implements Serializable {
    @Column(name = "contractId")
    private Long contractId;

    @Column(name = "allowanceId")
    private Long allowanceId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "unit")
    private String unit;
}
