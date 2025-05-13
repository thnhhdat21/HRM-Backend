package vn.tdsoftware.hrm_backend.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.DecisionDAO;
import vn.tdsoftware.hrm_backend.dao.LetterDAO;
import vn.tdsoftware.hrm_backend.dto.contract.request.EndContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.response.EndJobCurrentDate;
import vn.tdsoftware.hrm_backend.entity.Contract;
import vn.tdsoftware.hrm_backend.entity.ContractGeneral;
import vn.tdsoftware.hrm_backend.repository.ContractGeneralRepository;
import vn.tdsoftware.hrm_backend.repository.ContractRepository;
import vn.tdsoftware.hrm_backend.service.ContractService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContractScheduler {
    private final ContractService contractService;
    private final LetterDAO letterDAO;
    private final DecisionDAO decisionDAO;
    private final ContractGeneralRepository contractGeneralRepository;
    private final ContractRepository contractRepository;

    // hết hợp đồng với quyết định nghỉ việc
    @Scheduled(cron = "0 0 0 * * *")
    public void endJobWithDecision() {
        log.info("Start endJobWithDecision");
        LocalDate today = LocalDate.now();
        List<EndJobCurrentDate> endJobList = decisionDAO.getListDecisionCurrentDate(today);
        if (!endJobList.isEmpty()) {
            for (EndJobCurrentDate endJobCurrentDate : endJobList) {
                log.info("endJobWithDecision with {}", endJobCurrentDate.getContractId());
                contractService.endContract(EndContractRequest.builder()
                                .contractId(endJobCurrentDate.getContractId())
                                .reasonLiquidation(endJobCurrentDate.getReason())
                                .dateLiquidation(endJobCurrentDate.getCurrentDate())
                        .build());
            }
        }
        log.info("End endJobWithDecision with");
    }
    // hết hợp đồng với đơn xin nghỉ việc
    @Scheduled(cron = "0 0 0 * * *")
    public void endJobWithLetter() {
        log.info("Start endJobWithLetter");
        LocalDate today = LocalDate.now();
        List<EndJobCurrentDate> endJobList = letterDAO.getListLetterCurrentDate(today);
        if (!endJobList.isEmpty()) {
            for (EndJobCurrentDate endJobCurrentDate : endJobList) {
                log.info("endJobWithLetter with {}", endJobCurrentDate.getContractId());
                contractService.endContract(EndContractRequest.builder()
                        .contractId(endJobCurrentDate.getContractId())
                        .reasonLiquidation(endJobCurrentDate.getReason())
                        .dateLiquidation(endJobCurrentDate.getCurrentDate())
                        .build());
            }
        }
        log.info("end endJobWithLetter");
    }

    //Thanh lý hơp đồng
    @Scheduled(cron = "0 0 0 * * *")
    public void endJobWithLiquidation() {
        log.info("Start endJobWithLiquidation");
        LocalDate today = LocalDate.now();
        List<ContractGeneral> endJobList = contractGeneralRepository.findAllByDateLiquidation(today);
        if (!endJobList.isEmpty()) {
            for (ContractGeneral endJobCurrentDate : endJobList) {
                log.info("endJobWithLiquidation with {}", endJobCurrentDate.getContractOriginal());
                contractService.endContract(EndContractRequest.builder()
                        .contractId(endJobCurrentDate.getContractOriginal())
                        .reasonLiquidation(endJobCurrentDate.getReasonLiquidation())
                        .dateLiquidation(endJobCurrentDate.getDateLiquidation())
                        .build());
            }
        }
        log.info("end endJobWithLiquidation");

    }

    //hết hiệu lực hợp đồng
    @Scheduled(cron = "0 0 0 * * *")
    public void expireContract() {
        log.info("Start expireContract");
        LocalDate today = LocalDate.now();
        List<Contract> listContractExpire = contractRepository.findAllByDateEndAndParentIsNullAndIsEnabled(today, true)
                .orElse(new ArrayList<>());
        if (!listContractExpire.isEmpty()) {
            for (Contract contract : listContractExpire) {
                log.info("expireContract with {}", contract.getId());
                contractService.expireContract(contract.getId(), false);
            }
        }
        log.info("end expireContract");

    }

    //kích hoạt hợp đồng
    @Scheduled(cron = "0 5 0 * * *")
    public void activeContract() {
        log.info("Start activeContract");
        LocalDate today = LocalDate.now();
        List<Contract> listContractExpire = contractRepository.findAllByDateStartAndIsEnabled(today, true)
                .orElse(new ArrayList<>());
        if (!listContractExpire.isEmpty()) {
            for (Contract contract : listContractExpire) {
                log.info("activeContract with {}", contract.getEmployeeId());
                if (contract.getParent() == null) {
                    contract.setIsActive(true);
                }
                contractService.activeContract(contract.getEmployeeId(), contract);
            }
        }
        log.info("end activeContract");
    }
}
