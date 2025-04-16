package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.ContractType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    List<ContractType> findAllByIsEnabled(boolean isEnabled);
    Optional<ContractType> findByIdAndIsEnabled(long id, boolean isEnabled);
}
