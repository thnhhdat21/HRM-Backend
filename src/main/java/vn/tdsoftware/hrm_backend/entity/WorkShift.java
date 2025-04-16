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
@Table
public class WorkShift extends BaseEntity<Long> implements Serializable {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "timeIn")
    private LocalTime timeIn;

    @Column(name = "timeOut")
    private LocalTime timeOut;

    @Column(name = "nextDayEnabled")
    private Boolean nextDayEnabled;

    @Column(name = "breakStartTime")
    private LocalTime breakStartTime;

    @Column(name = "breakEndTime")
    private LocalTime breakEndTime;

    @Column(name = "totalTime")
    private Double totalTime;

    @Column(name = "totalWorkDay")
    private Double totalWorkDay;

    @Column(name = "checkInFirst")
    private LocalTime checkinFirst;

    @Column(name = "checkoutLater")
    private LocalTime checkoutLater;

    @Column(name = "autoTimeKeeping")
    private Boolean autoTimeKeeping;

    @Column(name = "autoCheckoutForPosition")
    private Boolean autoCheckoutForPosition;
}
