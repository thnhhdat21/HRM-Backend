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
@Table(name = "InsuranceRatioDetail")
public class InsuranceRatioDetail extends BaseEntity<Long> implements Serializable {
    @Column(name = "insuranceRatioId")
    private Integer insuranceRatioId;

    @Column(name = "insuranceType")
    private Integer insuranceType;

    @Column(name = "ratio")
    private Double ratio;

    @Column(name = "companyPay")
    private Double companyPay;
}
