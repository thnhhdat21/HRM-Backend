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
@Table(name = "contractTypeHasAllowance")
public class ContractTypeHasAllowance extends BaseEntity<Long> implements Serializable {
    @Column(name = "contractTypeId")
    private Long contractTypeId;

    @Column(name = "allowanceId")
    private Long allowanceId;
}
