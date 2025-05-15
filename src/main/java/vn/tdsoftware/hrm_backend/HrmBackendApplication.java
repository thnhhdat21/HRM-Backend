package vn.tdsoftware.hrm_backend;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.tdsoftware.hrm_backend.dto.contract.request.ContractRequest;
import vn.tdsoftware.hrm_backend.entity.*;
import vn.tdsoftware.hrm_backend.repository.*;
import vn.tdsoftware.hrm_backend.scheduled.ContractScheduler;
import vn.tdsoftware.hrm_backend.service.ContractService;
import vn.tdsoftware.hrm_backend.service.SalaryService;
import vn.tdsoftware.hrm_backend.util.EmployeeUtil;
import vn.tdsoftware.hrm_backend.util.constant.EmployeeConstant;
import vn.tdsoftware.hrm_backend.util.constant.InsuranceUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@RequiredArgsConstructor
public class HrmBackendApplication implements CommandLineRunner {
//
//	@Autowired
//	private EmployeeRepository employeeRepository;
//
//	@Autowired
//	private TimeKeepingRepository timeKeepingRepository;
//
//	@Autowired
//	private OnLeaveRepository onLeaveRepository;
//
//	@Autowired
//	private ContractService contractService;
//
//	@Autowired
//	private SalaryService salaryService;
//
//	@Autowired
//	private InsuranceRepository insuranceRepository;

//	@Autowired
//	private ContractScheduler contractScheduler;

//	@Autowired
//	private ContractRepository contractRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HrmBackendApplication.class, args);
	}


//
//	public void generateAndSaveEmployeeData() {
//		// Tạo danh sách tên nhân viên không trùng lặp
//		List<String> names = Arrays.asList(
//				"Nguyễn Thị Lan", "Trần Văn Hải", "Phạm Thị Thanh", "Lê Minh Tuấn", "Vũ Hoàng Nam",
//				"Đỗ Thị Bích", "Mai Thị Kim", "Phan Văn Cường", "Hồ Thị Khuê", "Lê Thị Thu",
//				"Nguyễn Thị Hằng", "Trương Hoàng Minh", "Hồ Quang Hieu", "Nguyễn Thanh Sơn", "Phan Minh Tuấn",
//				"Vũ Anh Tuấn", "Lê Minh Hoàng", "Nguyễn Thị Mai", "Trần Thanh Tâm", "Lê Thị Lan",
//				"Nguyễn Hoàng Nam", "Trần Thị Mai", "Vũ Thị Thu", "Nguyễn Minh Cường", "Lê Minh Khôi",
//				"Nguyễn Đức Anh", "Phạm Văn Hùng", "Nguyễn Văn Thanh", "Phan Minh Hải", "Lê Thị Hương",
//				"Trần Thị Lan", "Nguyễn Thị Thu", "Vũ Thị Khuê", "Phan Thị Bích", "Trương Hoàng Minh",
//				"Nguyễn Thị Thảo", "Lê Minh Tuấn", "Hồ Quang Hieu", "Trần Văn Thanh", "Phan Minh Huy",
//				"Lê Thị Dung", "Nguyễn Hữu Kiên", "Trần Hoàng Minh", "Phan Anh Tuấn", "Nguyễn Thị Kim",
//				"Phạm Minh Tâm", "Trương Hoàng Hieu", "Nguyễn Thị Dung", "Lê Minh Tâm", "Trần Thị Kim",
//				"Nguyễn Hoàng Thủy", "Phan Anh Hùng", "Vũ Hoàng Quân", "Lê Thị Lan", "Trần Hoàng Anh",
//				"Nguyễn Quang Hieu", "Phan Minh Hoàng", "Lê Thị Dung", "Nguyễn Văn Hải", "Trần Thị Thảo",
//				"Phan Minh Hưng", "Trương Minh Khôi", "Hồ Thị Thu", "Nguyễn Hoàng Sơn", "Trần Hoàng Tâm",
//				"Phạm Minh An", "Lê Thị Minh", "Nguyễn Thị Minh", "Phan Thị Lan", "Nguyễn Thị Linh",
//				"Trần Minh Hồng", "Lê Hoàng Duy", "Nguyễn Anh Thư", "Hồ Thị Bích", "Trần Thị Thanh",
//				"Nguyễn Minh Hoàng", "Phan Minh Tuấn", "Trần Thị Thanh", "Lê Minh Tâm", "Nguyễn Thị Quỳnh",
//				"Trương Minh Hương", "Nguyễn Hoàng Hòa", "Phan Thị Hoàng", "Lê Thị Tường", "Trần Thị Bích",
//				"Nguyễn Minh Thi", "Phan Quỳnh Anh", "Vũ Quang Hieu", "Lê Hoàng Minh", "Nguyễn Thị Bình",
//				"Phan Anh Tuấn", "Trần Văn Kiên", "Nguyễn Hoàng Tuấn", "Lê Hoàng Minh", "Phan Minh Hòa",
//				"Trương Thị Bích", "Phan Minh Thu", "Lê Quang Minh", "Nguyễn Thị Duy", "Trần Anh Hưng",
//				"Nguyễn Hồng Sơn", "Phan Minh Thảo", "Trương Thị Mai", "Nguyễn Minh Tuấn", "Lê Thị An",
//				"Phan Hoàng Minh", "Lê Thị Bích", "Nguyễn Minh Thi", "Trần Thị Nhung", "Phan Thị Lan",
//				"Nguyễn Thanh Linh", "Trần Minh Tuấn", "Lê Minh Minh", "Phan Minh Lan", "Nguyễn Minh Bảo",
//				"Phạm Minh Đức", "Lê Minh Nguyên", "Nguyễn Thị Thảo", "Trần Thanh Hiền", "Phan Thanh Hải",
//				"Lê Hoàng Quân", "Nguyễn Thị Thanh", "Trương Minh Thi", "Hồ Minh Tuấn", "Nguyễn Đức Thành",
//				"Phan Minh Thi", "Trần Minh Hòa", "Lê Thị Khuê", "Nguyễn Thị Dung", "Trương Minh Quân",
//				"Nguyễn Thị Kim", "Phan Minh Linh", "Lê Minh Thu", "Trần Minh An", "Phan Anh Sơn",
//				"Nguyễn Minh Trí", "Lê Thị Hoàng", "Nguyễn Thị Sơn", "Phan Anh Linh", "Nguyễn Minh Vũ",
//				"Trần Thị Phương", "Lê Quang Thảo", "Nguyễn Minh Hùng", "Phan Thị Hoàng", "Trần Quang Linh",
//				"Lê Thị Bình", "Trần Minh Thi", "Phan Minh Kiên", "Nguyễn Minh Khuê", "Lê Thị Thi",
//				"Trần Anh Linh", "Phan Thị An", "Lê Minh Sơn", "Nguyễn Hoàng Bích", "Trần Minh Duy"
//		);
//
//		// Tạo danh sách nhân viên
//		List<Employee> employees = new ArrayList<>();
//		for (int i = 0; i < names.size(); i++) {
//			String name = names.get(i);
//			employees.add(generateEmployeeWithName(name, i + 1));  // Tạo nhân viên với mã nhân viên duy nhất
//		}
//
//		// Lưu vào DB
//		employeeRepository.saveAll(employees);
//	}
//
//	private Employee generateEmployeeWithName(String fullName, int index) {
//		Employee employee = new Employee();
//
//		// Tạo mã nhân viên (TS001 -> TS150)
//		String employeeCode = "TS" + String.format("%03d", index);
//
//		// Tạo thông tin khác (số điện thoại, email, ngày sinh...)
//		String phoneNumber = "090" + (index % 100000000); // Ví dụ số điện thoại
//		String email = fullName.toLowerCase().replace(" ", ".") + "@example.com"; // Ví dụ email
//
//		employee.setFullName(fullName);
//		employee.setEmployeeCode(employeeCode);
//		employee.setDateOfBirth(LocalDate.of(1990, 1, 1).plusDays(index));  // Ngày sinh tăng dần
//		employee.setPhoneNumber(phoneNumber);
//		employee.setEmail(email);
//
//		// Các trường dữ liệu khác cũng tạo như trước
//		employee.setStatus(1);  // Trạng thái 'active'
//		employee.setGender(index % 2 == 0);  // Giới tính
//		// Còn lại các trường khác như taxCode, avatar... tùy chỉnh như trước
//
//		return employee;
//	}

//	public void generateAndSaveTimeKeepingData() {
//		// Danh sách nhân viên (giả sử bạn đã có danh sách nhân viên từ trước)
//		List<Employee> employees = employeeRepository.findAll(); // Tải tất cả nhân viên từ DB
//
//		// Xử lý dữ liệu chấm công cho tháng 3 và tháng 4 năm 2025
//		List<TimeKeeping> timeKeepings = new ArrayList<>();
//
//		// Xử lý tháng 3/2025
//		generateTimeKeepingForMonth(employees, 3, 2025, timeKeepings);
//
//		// Xử lý tháng 4/2025
//		generateTimeKeepingForMonth(employees, 4, 2025, timeKeepings);
//
//		// Lưu dữ liệu vào DB
//		timeKeepingRepository.saveAll(timeKeepings);
//	}
//
//	private void generateTimeKeepingForMonth(List<Employee> employees, int month, int year, List<TimeKeeping> timeKeepings) {
//		// Tạo đối tượng LocalDate cho tháng đầu và cuối
//		LocalDate startDate = LocalDate.of(year, month, 1);
//		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
//
//		// Duyệt qua từng ngày trong tháng
//		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
//			// Kiểm tra nếu ngày là ngày làm việc (không phải T7, CN)
//			if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
//				// Tạo bản ghi chấm công cho tất cả nhân viên
//				for (Employee employee : employees) {
//					TimeKeeping timeKeeping = new TimeKeeping();
//
//					// Thiết lập thông tin chấm công
//					timeKeeping.setEmployeeId(employee.getId());
//					timeKeeping.setDate(date);
//					timeKeeping.setTimeIn(LocalTime.of(8, 30)); // Giờ vào (ví dụ: 8:00)
//					timeKeeping.setTimeOut(LocalTime.of(17, 30)); // Giờ ra (ví dụ: 17:00)
//					timeKeeping.setWorkDay(8.0); // Số giờ làm việc (ví dụ: 8 giờ)
//					timeKeeping.setTimeLate(LocalTime.of(0, 0)); // Không muộn
//					timeKeeping.setTimeEarly(LocalTime.of(0, 0)); // Không ra sớm
//
//					// Thêm vào danh sách chấm công
//					timeKeepings.add(timeKeeping);
//				}
//			}
//		}
//	}

//	public static List<ContractRequest> generateContracts(List<Employee> allEmployees) {
//		List<ContractRequest> contractRequests = new ArrayList<>();
//		List<Long> departments = List.of(4L, 5L, 6L, 8L, 9L, 11L, 12L, 14L, 15L, 16L);  // Các phòng ban
//
//		// Ngày hiện tại và 5 năm sau
//		LocalDate dateStart = LocalDate.of(2025, 4, 27);
//		LocalDate dateEnd = dateStart.plusYears(5);
//		LocalDate dateSign = dateStart;
//
//		Random random = new Random();
//		List<Employee> employeesInDepartment = new ArrayList<>(allEmployees);
//		// Duyệt qua các phòng ban
//		for (Long departmentId : departments) {
//			Set<Employee> selectedEmployees = new HashSet<>();  // Dùng Set để tránh trùng lặp
//
//			// Chọn ngẫu nhiên 10 nhân viên cho mỗi phòng ban
//			while (selectedEmployees.size() < 16 && !employeesInDepartment.isEmpty()) {
//				int index = random.nextInt(employeesInDepartment.size());
//				Employee selected = employeesInDepartment.remove(index);
//				selectedEmployees.add(selected);  // Set tự động tránh trùng lặp
//			}
//
//			// Tạo hợp đồng cho mỗi nhân viên trong phòng ban
//			for (Employee employee : selectedEmployees) {
//				ContractRequest contractRequest = ContractRequest.builder()
//						.employeeId(employee.getId())
//						.contractCode("CONTRACT_" + employee.getEmployeeCode())
//						.contractId(0)  // Contract ID = 0
//						.contractType(1)  // Contract type = 1
//						.department(departmentId)  // Gán phòng ban vào hợp đồng
//						.jobPosition(1L)  // Job position của nhân viên
//						.contractMethod("Full-time")  // Giả sử là hợp đồng full-time
//						.salaryGross(5000000)  // Giả sử lương gross là 5 triệu
//						.dateStart(dateStart)  // Ngày bắt đầu hợp đồng
//						.dateEnd(dateEnd)  // Ngày kết thúc hợp đồng (5 năm sau)
//						.dateSign(dateSign)  // Ngày ký hợp đồng
//						.parent(null)  // Parent = null
//						.createType(1)  // CreateType = 1
//						.description("Standard full-time contract")  // Mô tả hợp đồng
//						.build();
//
//				contractRequests.add(contractRequest);
//			}
//		}
//
//		return contractRequests;
//	}
// cccc

//	public void generateOnLeave() {
//		List<Employee> employees = employeeRepository.findAll(); // Tải tất cả nhân viên từ DB
//
//		List<OnLeave> onLeaves = new ArrayList<>();
//
//		for (Employee employee : employees) {
//			onLeaves.add(OnLeave.builder()
//							.employeeId(employee.getId())
//							.regulaDay(12.0)
//							.usedDay(0.0)
//							.year(2025)
//							.seniorDay(0.0)
//					.build());
//		}
//
//		onLeaveRepository.saveAll(onLeaves);
//	}


//	public void generateInsurance () {
//		YearMonth yearMonth = YearMonth.now();
//		List<Employee> listEmployees = employeeRepository.findAllByStatusAndIsEnabled(EmployeeConstant.EMPLOYEE_STATUS_WORKING, true);
//		List<Insurance> insurances = new ArrayList<>();
//		for (Employee employee : listEmployees) {
//			insurances.add(Insurance.builder()
//							.employeeId(employee.getId())
//							.yearMonth(yearMonth.toString())
//							.status(InsuranceUtil.INSURANCE_STATUS_NORMAL)
//					.build());
//		}
//		if(!insurances.isEmpty()) {
//			insuranceRepository.saveAll(insurances);
//		}
//	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		generateInsurance();
//		salaryService.calculationSalary();
//		generateOnLeave();
//		generateAndSaveEmployeeData();
//		generateAndSaveTimeKeepingData();

//		List<ContractRequest> contractRequests = generateContracts(employeeRepository.findAll());
//		for (ContractRequest contractRequest : contractRequests) {
//			contractService.createContract(contractRequest);
//		}

//		contractScheduler.activeContract();
//		System.out.println(passwordEncoder.encode("thanhdat2"));
	}
}
