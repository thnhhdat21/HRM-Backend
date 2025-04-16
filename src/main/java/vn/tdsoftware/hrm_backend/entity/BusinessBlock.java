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
@Table(name = "BusinessBlock")
public class BusinessBlock extends BaseEntity<Integer> implements Serializable {
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;
}
