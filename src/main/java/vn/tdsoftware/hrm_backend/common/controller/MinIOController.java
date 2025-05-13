package vn.tdsoftware.hrm_backend.common.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.service.MinIOService;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class MinIOController {
    private final MinIOService minIOService;

    @PostMapping("/get-file")
    public ResponseEntity<InputStreamResource> getFile(@RequestParam("fileName") String fileName) {
        InputStreamResource inputStream = minIOService.getFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStream);
    }

}
