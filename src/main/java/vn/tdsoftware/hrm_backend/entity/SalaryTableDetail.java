package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SalaryTableDetail")
@ToString
public class SalaryTableDetail extends BaseEntity<Long> implements Serializable {
    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "department")
    private Long department;

    @Column(name = "jobPosition")
    private Long jobPosition;

    @Column(name = "salaryTableId")
    private Long salaryTableId;

    @Column(name = "workDayReal")
    private Integer workDayReal;

    @Column(name = "totalWorkDay")
    private Integer totalWorkDay;

    @Column(name = "salaryGross")
    private Integer salaryGross;

    @Column(name = "salaryWorkDay")
    private Integer salaryWorkDay;

    @Column(name = "totalAllowance")
    private Integer totalAllowance;

    @Column(name = "penalty")
    private Integer penalty;

    @Column(name = "reward")
    private Integer reward;

    @Column(name = "salaryOTOnWeek")
    private Integer salaryOTOnWeek;

    @Column(name = "salaryOTLastWeek")
    private Integer salaryOTLastWeek;

    @Column(name = "salaryOTHoliday")
    private Integer salaryOTHoliday;

    @Column(name = "totalInsurance")
    private Integer totalInsurance;

    @Column(name = "totalTax")
    private Integer totalTax;

    @Column(name = "salaryReal")
    private Integer salaryReal;
}
