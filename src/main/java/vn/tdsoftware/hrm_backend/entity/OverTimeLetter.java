package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OverTimeLetter")
public class OverTimeLetter extends BaseEntity<Long> implements Serializable {
    @Column(name = "letterId")
    private Long letterId;

    @Column(name = "timeStart")
    private Timestamp timeStart;

    @Column(name = "timeEnd")
    private Timestamp timeEnd;

    @Column(name = "total")
    private Double total;
}
