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
@Table(name = "ContractGeneral")
public class ContractGeneral extends BaseEntity<Long> implements Serializable {
    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Integer status;

    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "departmentId")
    private Long departmentId;

    @Column(name = "jobPositionId")
    private Long jobPositionId;

    @Column(name = "type")
    private Long type;

    @Column(name = "method")
    private String method;

    @Column(name = "dateStart")
    private LocalDate dateStart;

    @Column(name = "dateEnd")
    private LocalDate dateEnd;

    @Column(name = "dateSign")
    private LocalDate dateSign;

    @Column(name = "dateLiquidation")
    private LocalDate dateLiquidation;

    @Column(name = "dateOnBoard")
    private LocalDate dateOnBoard;

    @Column(name = "reasonLiquidation", columnDefinition = "varchar(512)")
    private String reasonLiquidation;

    @Column(name = "salaryGross")
    private Integer salaryGross;

    @Column(name = "state")
    private Integer state;
}
