package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.BusinessBlock;

@Repository
public interface BusinessBlockRepository extends JpaRepository<BusinessBlock, Integer> {
    BusinessBlock findBusinessBlockByIdAndIsEnabled(Integer id, boolean b);
}
