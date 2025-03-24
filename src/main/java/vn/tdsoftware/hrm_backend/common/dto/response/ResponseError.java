package vn.tdsoftware.hrm_backend.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class ResponseError implements Serializable {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    private LocalDateTime timestamp;
    private boolean success;
    private String path;
    private String code;
    private String error;
    private String message;

    public ResponseError() {
    }

    public ResponseError(LocalDateTime timestamp, boolean success, String path, String code, String error, String message) {
        this.timestamp = timestamp;
        this.success = success;
        this.path = path;
        this.code = code;
        this.error = error;
        this.message = message;
    }
}