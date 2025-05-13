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
@Table(name = "InOutAndEndWorkLetter")
public class InOutAndEndWorkLetter extends BaseEntity<Long> implements Serializable {
    @Column(name = "letterId")
    private long letterId;

    @Column(name = "dateRegis")
    private LocalDate dateRegis;
}
