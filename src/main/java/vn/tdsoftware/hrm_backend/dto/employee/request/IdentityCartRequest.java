package vn.tdsoftware.hrm_backend.dto.employee.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class IdentityCartRequest {
    private long employeeId;
    private MultipartFile fontIdCard;
    private MultipartFile backIdCard;
}
