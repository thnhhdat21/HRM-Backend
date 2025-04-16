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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AssetUnit")
public class AssetUnit extends BaseEntity<Long> implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;
}
