package vn.tdsoftware.hrm_backend.entity;

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
public class SalaryTableDetail extends BaseEntity<Long> implements Serializable {

    private Long salaryTableId;
    private Integer totalWorkDay;
    private Integer salaryGross;
    private Integer salaryWorkDay;
    private Integer totalAllowance;
    private Integer penalty;
    private Integer reward;
    private Integer salaryOTOnWeek;
    private Integer salaryOTLastWeek;
    private Integer salaryOTHoliday;
    private Integer totalInsurance;
    private Integer totalTax;
    private Integer salaryReal;
}
