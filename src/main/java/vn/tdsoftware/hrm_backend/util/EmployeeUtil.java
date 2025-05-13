package vn.tdsoftware.hrm_backend.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;

@Component
@RequiredArgsConstructor
public class EmployeeUtil {

    private final EmployeeRepository employeeRepository;

    public String generateEmployeeCode() {
        long count = employeeRepository.count();
        String number = count < 99 ? "0" + count : String.valueOf(count);
        return "TDS" + number;
    }
}