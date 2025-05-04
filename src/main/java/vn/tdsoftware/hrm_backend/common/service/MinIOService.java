package vn.tdsoftware.hrm_backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinIOService {
    void uploadFile(String fileName, MultipartFile file);
    InputStream getFile(String pathFile);
}
