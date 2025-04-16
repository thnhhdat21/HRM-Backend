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
@Table(name = "contractType")
public class ContractType extends BaseEntity<Long> implements Serializable {
    @Column(name = "name")
    private String name;

    @Column(name = "type", columnDefinition = "varchar(512)")
    private String type;

    @Column(name = "method")
    private String method;

    @Column(name = "term")
    private Integer term;

    @Column(name = "unit")
    private String unit;

    @Column(name = "insurance")
    private Boolean insurance;

    @Column(name = "status")
    private String status;
}