package vn.tdsoftware.hrm_backend.dto.education.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EducationRequest {
    private Long id;
    private Long employeeId;
    private LocalDate toMonth;
    private LocalDate fromMonth;
    private String level;
    private String placeTraining;
    private String major;
    private String methodTraining;
    private String isUpdate;
}
