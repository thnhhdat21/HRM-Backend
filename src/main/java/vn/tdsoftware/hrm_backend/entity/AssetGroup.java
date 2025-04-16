package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AssetGroup")
public class AssetGroup extends BaseEntity<Long> implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "parentId")
    private Long parentId;

    @Column(name = "status")
    private String status;
}
