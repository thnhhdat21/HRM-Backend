package vn.tdsoftware.hrm_backend.dto.timekeeping.response;

import lombok.*;

import java.time.LocalTime;
import java.util.List;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTimeKeepingResponse {
    private long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String jobPosition;
    private Double onLeaveTotal;
    private Double onLeaveUsed;
    private Double overTimeTotal;
    private int totalLateDay;
    private double totalWorkDay;
    private List<TimeKeepingResponse> timeKeeping;
}
