package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Holiday")
public class Holiday extends BaseEntity<Long> implements Serializable {

    @Column(name = "reason")
    private String reason;
    @Column(name = "type")
    private Integer type;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "description")
    private String description;
}
