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
@Table(name = "RoleHasPermission")
public class RoleHasPermission extends BaseEntity<Integer> implements Serializable {
    @Column(name = "roleId")
    private Integer roleId;

    @Column(name = "permissionId")
    private Integer permissionId;
}
