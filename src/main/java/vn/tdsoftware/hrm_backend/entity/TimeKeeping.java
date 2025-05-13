package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TimeKeeping")
public class TimeKeeping extends BaseEntity<Long> implements Serializable {
    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "timeIn")
    private LocalTime timeIn;

    @Column(name = "timeOut")
    private LocalTime timeOut;

    @Column(name = "workDay")
    private Double workDay;

    @Column(name = "timeLate")
    private LocalTime timeLate;

    @Column(name = "timeEarly")
    private LocalTime timeEarly;

    @Column(name = "timeSheetId")
    private Integer timeSheetId;

    @Column(name = "isLate")
    private Boolean isLate;

    @Column(name = "workingOnHoliday")
    private Boolean workingOnHoliday;
}
