package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Department")
public class Department extends BaseEntity<Long> implements Serializable {
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "businessBlockId")
    private Integer businessBlockId;

    @Column(name = "departmentLevel")
    private Integer departmentLevel;

    @Column(name = "parentId")
    private Long parentId;
}
