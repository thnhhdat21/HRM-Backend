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
@Table(name = "SalaryTable")
public class SalaryTable extends BaseEntity<Long> implements Serializable {
    @Column(name = "yearMonth")
    private String yearMonth;

    @Column(name = "name")
    private String name;

    @Column(name = "numberEmployee")
    private Integer numberEmployee;

    @Column(name = "totalAmount")
    private Long totalAmount;

    @Column(name = "status")
    private Integer status;
}
