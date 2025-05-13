package vn.tdsoftware.hrm_backend.common.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;


public interface MinIOService {
    void uploadFile(String fileName, MultipartFile file);
    InputStreamResource getFile(String pathFile);
}
