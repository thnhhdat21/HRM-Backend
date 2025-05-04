package vn.tdsoftware.hrm_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.tdsoftware.hrm_backend.common.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LeaveLetter")
public class LeaveLetter extends BaseEntity<Long> implements Serializable {

    @Column(name = "letterId")
    private Long letterId;

    @Column(name = "dateStart")
    private LocalDateTime dateStart;

    @Column(name = "dateEnd")
    private LocalDateTime dateEnd;

    @Column(name = "total")
    private Double total;
}
