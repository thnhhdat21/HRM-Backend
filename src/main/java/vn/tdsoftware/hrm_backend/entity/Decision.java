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
@Table(name = "Decision")
public class Decision extends BaseEntity<Long> implements Serializable {
    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "type")
    private int type;

    @Column(name = "reason" , columnDefinition = "varchar(512)")
    private String reason;

    @Column(name = "state")
    private int state;
}
