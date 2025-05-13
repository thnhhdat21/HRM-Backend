package vn.tdsoftware.hrm_backend.dto.letter.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class OverTimeLetterOfEmployee {
    private LocalDate dateRegis;
    private int total;
}
