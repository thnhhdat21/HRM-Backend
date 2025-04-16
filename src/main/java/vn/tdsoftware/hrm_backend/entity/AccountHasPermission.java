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
@Table(name = "AccountHasPermission")
public class AccountHasPermission extends BaseEntity<Long> implements Serializable {
    @Column(name = "accountId")
    private Long accountId;

    @Column(name = "permissionId")
    private Integer permissionId;
}
