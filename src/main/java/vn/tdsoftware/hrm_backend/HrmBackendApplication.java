package vn.tdsoftware.hrm_backend;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import vn.tdsoftware.hrm_backend.entity.AccountBank;
import vn.tdsoftware.hrm_backend.entity.Employee;
import vn.tdsoftware.hrm_backend.repository.AccountBankRepository;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;
import vn.tdsoftware.hrm_backend.service.SalaryService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


@SpringBootApplication
@EnableScheduling
@EnableCaching
@RequiredArgsConstructor
public class HrmBackendApplication implements CommandLineRunner {
	@Autowired
	private SalaryService salaryService;

	@Autowired
	private EmployeeRepository employeeRepository;


	public static void main(String[] args) {
		SpringApplication.run(HrmBackendApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		salaryService.calculationSalary();
	}
}
