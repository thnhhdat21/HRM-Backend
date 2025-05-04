package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LetterReason")
public class LetterReason extends BaseEntity<Long> implements Serializable {

    @Column(name = "letterTypeId")
    private Integer letterTypeId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "maximum")
    private Integer maximum;

    @Column(name = "unit")
    private String unit;

    @Column(name = "workDayEnabled")
    private Boolean workDayEnabled;

    @Column(name = "goLate")
    private LocalTime goLate;

    @Column(name = "backEarly")
    private LocalTime backEarly;

    @Column(name = "status")
    private String status;

    @Column(name = "description", columnDefinition = "varchar(512)")
    private String description;
}
