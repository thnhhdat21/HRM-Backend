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
    @Column(name = "singedContract")
    private Boolean singedContract;

    @Column(name = "returnedLeaveTmp")
    private Boolean returnedLeaveTmp;

    @Column(name = "leaveTmp")
    private Boolean leaveTmp;

    @Column(name = "unpaidLeave")
    private Boolean unpaidLeave;
}
