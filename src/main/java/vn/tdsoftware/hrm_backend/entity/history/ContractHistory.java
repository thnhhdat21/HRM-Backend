package vn.tdsoftware.hrm_backend.entity.history;

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
@Table(name = "ContractHistory")
public class ContractHistory extends BaseEntity<Long> implements Serializable {

    @Column(name = "contractId")
    private Long contractId;

    @Column(name = "state")
    private Integer state;

    @Column(name = "title", columnDefinition = "varchar(1024)")
    private String title;
}
