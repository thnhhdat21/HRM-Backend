package vn.tdsoftware.hrm_backend.dto.salarytable.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SalaryTableResponse {
    private String yearMonth;
    private long id;
    private String nameSalaryTable;
    private int status;
    private int numberEmployee;
    private long totalAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd ", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
}
