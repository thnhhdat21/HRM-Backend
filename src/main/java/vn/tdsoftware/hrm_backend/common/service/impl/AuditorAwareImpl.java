package vn.tdsoftware.hrm_backend.common.service.impl;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.common.entity.CurrentUserHolder;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Long employeeId = CurrentUserHolder.getEmployeeId();
        return employeeId != null ? Optional.of(employeeId.intValue()) : Optional.empty();
    }
}
