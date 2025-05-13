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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TimeSheet")
public class TimeSheet extends BaseEntity<Integer> implements Serializable {
    @Column(name = "yearMonth")
    private String yearMonth;
    @Column(name = "isClosed")
    private Boolean isClosed;
}
