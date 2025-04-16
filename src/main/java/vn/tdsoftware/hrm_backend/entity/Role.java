package vn.tdsoftware.hrm_backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Role")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role extends BaseEntity<Integer> implements Serializable {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "accountAdmin")
    private Boolean accountAdmin;

    @Column(name = "accountDefault")
    private Boolean accountDefault;

    @Column(name = "description", length = 500, columnDefinition = "varchar(500)")
    private String description;
}
