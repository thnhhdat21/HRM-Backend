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
@Table(name = "AccountBank")
public class AccountBank extends BaseEntity<Long> implements Serializable {

    @Column(name = "employeeId")
    private Long employeeId;
    @Column(name = "accountName")
    private String accountName;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "bankName")
    private String bankName;
}
