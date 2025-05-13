package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.SalaryTableDAO;
import vn.tdsoftware.hrm_backend.dao.SalaryTableDetailDAO;
import vn.tdsoftware.hrm_backend.dto.account.response.CurrentAccountDTO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.*;
import vn.tdsoftware.hrm_backend.entity.SalaryTable;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.SalaryTableMapper;
import vn.tdsoftware.hrm_backend.repository.SalaryTableRepository;
import vn.tdsoftware.hrm_backend.service.SalaryTableService;
import vn.tdsoftware.hrm_backend.util.PerSalaryUtil;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SalaryTableServiceImpl implements SalaryTableService {
    private final SalaryTableRepository salaryTableRepository;
    private final SalaryTableDAO salaryTableDAO;
    private final SalaryTableDetailDAO salaryTableDetailDAO;
    private final PerSalaryUtil perSalaryUtil;

    @Override
    public List<SalaryTableResponse> getListSalaryTable() {
        List<SalaryTable> salaryTableList = salaryTableRepository.findByIsEnabledOrderByYearMonthDesc(true).orElseThrow(
                () -> new BusinessException(ErrorCode.SALARY_TABLE_IS_EMPTY)
        );
        List<SalaryTableResponse> salaryTableResponseList = new ArrayList<>();
        for (SalaryTable salaryTable : salaryTableList) {
            salaryTableResponseList.add(SalaryTableMapper.mapToSalaryTableResponse(salaryTable));
        }
        return salaryTableResponseList;
    }

    @Override
    public List<SalaryTableResponse> getListSalaryTableOfDepartment() {
        List<SalaryTableResponse> salaryTableResponseList = salaryTableDAO.getListSalaryTableOfDepartment(CurrentAccountDTO.getDepartmentId());
        if (salaryTableResponseList.isEmpty()){
            throw new BusinessException(ErrorCode.SALARY_TABLE_IS_EMPTY);
        }
        return salaryTableResponseList;
    }

    @Override
    public List<SalaryDetailResponse> getListSalaryDetail(EmployeeFilter filter) {
        perSalaryUtil.checkSameDepartmentByFilter(filter);
        List<SalaryDetailResponse> response = salaryTableDetailDAO.getListSalaryDetail(filter);
        if (response.isEmpty()) {
            throw new BusinessException(ErrorCode.SALARY_TABLE_IS_EMPTY);
        }
        return response;
    }

    @Override
    public int getCountSalaryDetail(EmployeeFilter filter) {
        perSalaryUtil.checkSameDepartmentByFilter(filter);
        return salaryTableDetailDAO.getCountSalaryDetail(filter);
    }

    @Override
    public List<TaxResponse> getListTax(EmployeeFilter filter) {
        perSalaryUtil.checkSameDepartmentByFilter(filter);
        List<TaxDTO> listTax = salaryTableDetailDAO.getListTax(filter);
        List<TaxResponse> taxResponseList = new ArrayList<>();
        Set<String> employeeCodes = new HashSet<>();
        TaxResponse taxResponse = null;
        int month = 1;
        for (TaxDTO taxDTO : listTax) {
            if (!employeeCodes.contains(taxDTO.getEmployeeCode())) {
                employeeCodes.add(taxDTO.getEmployeeCode());
                taxResponse = new TaxResponse();
                taxResponse.setEmployeeId(taxDTO.getEmployeeId());
                taxResponse.setEmployeeName(taxDTO.getEmployeeName());
                taxResponse.setEmployeeCode(taxDTO.getEmployeeCode());
                taxResponse.setDepartment(taxDTO.getDepartment());
                taxResponse.setJobPosition(taxDTO.getJobPosition());
                taxResponse.setSalaryMonths(new ArrayList<>());
                taxResponseList.add(taxResponse);
                month = 1;
            }
            if ( taxDTO.getYearMonth() != null) {
                while(month < taxDTO.getYearMonth().getMonthValue()) {
                    taxResponse.getSalaryMonths().add(SalaryMonth.builder()
                            .yearMonth(YearMonth.of(taxDTO.getYearMonth().getYear(), month))
                            .salary(0)
                            .tax(0)
                            .build());
                    month++;
                }
                taxResponse.getSalaryMonths().add(SalaryMonth.builder()
                        .yearMonth(taxDTO.getYearMonth())
                        .salary(taxDTO.getSalary())
                        .tax(taxDTO.getTax())
                        .build());
                month++;
            }
        }
        if (taxResponseList.isEmpty()) {
            throw new BusinessException(ErrorCode.TAX_TABLE_IS_EMPTY);
        }
        return taxResponseList;
    }

    @Override
    public int getCountTax(EmployeeFilter filter) {
        perSalaryUtil.checkSameDepartmentByFilter(filter);
        return salaryTableDetailDAO.getCountTax(filter);
    }
}
