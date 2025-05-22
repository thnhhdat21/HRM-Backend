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
@Table(name = "Permission")
public class Permission extends BaseEntity<Integer> implements Serializable {
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description", length = 500, columnDefinition = "varchar(500)")
    private String description;

    @Column(name = "groupPer")
    private String groupPer;
}
