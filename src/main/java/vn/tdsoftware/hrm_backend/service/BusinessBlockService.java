package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.businessblock.request.BusinessBlockRequest;
import vn.tdsoftware.hrm_backend.dto.businessblock.response.BusinessBlockResponse;

import java.util.List;

public interface BusinessBlockService {
    List<BusinessBlockResponse> getListBusinessBlock();
    BusinessBlockResponse createBusinessBlock(BusinessBlockRequest request);
    BusinessBlockResponse updateBusinessBlock(BusinessBlockRequest request);
    void deleteBusinessBlock(int id);
}
