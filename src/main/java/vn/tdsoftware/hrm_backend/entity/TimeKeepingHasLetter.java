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
@Table(name = "TimeKeepingHasLetter")
public class TimeKeepingHasLetter extends BaseEntity<Long> implements Serializable {

    @Column(name = "timeKeepingId")
    private Long timeKeepingId;

    @Column(name = "letterId")
    private Long letterId;

}
