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
@Table(name = "Education")
public class Education extends BaseEntity<Long> implements Serializable {
    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "toMonth")
    private LocalDate toMonth;

    @Column(name = "fromMonth")
    private LocalDate fromMonth;

    @Column(name = "level")
    private String level;

    @Column(name = "placeTraining")
    private String placeTraining;

    @Column(name = "major")
    private String major;

    @Column(name = "methodTraining")
    private String methodTraining;
}
