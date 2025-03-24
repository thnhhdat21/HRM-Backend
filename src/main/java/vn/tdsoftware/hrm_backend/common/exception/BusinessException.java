package vn.tdsoftware.hrm_backend.common.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends RuntimeException{
    private final String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
}
