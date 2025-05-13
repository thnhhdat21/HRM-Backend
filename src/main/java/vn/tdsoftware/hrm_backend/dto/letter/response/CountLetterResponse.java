package vn.tdsoftware.hrm_backend.dto.letter.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CountLetterResponse {
    private int id;
    private String name;
    private int count;
}
