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
@Table(name = "Letter")
public class Letter extends BaseEntity<Long> implements Serializable {
    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "reasonId")
    private Long reasonId;

    @Column(name = "state")
    private Integer state;

    @Column(name = "description")
    private String description;
}
