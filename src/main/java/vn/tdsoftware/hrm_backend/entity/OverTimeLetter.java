package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OverTimeLetter")
public class OverTimeLetter extends BaseEntity<Long> implements Serializable {
    @Column(name = "dateRegis")
    private LocalDate dateRegis;

    @Column(name = "letterId")
    private Long letterId;

    @Column(name = "timeStart")
    private LocalTime timeStart;

    @Column(name = "timeEnd")
    private LocalTime timeEnd;

    @Column(name = "total")
    private Double total;

    @Column(name = "isNextDay")
    private Boolean isNextDay;

    @Column(name = "description")
    private String description;
}
