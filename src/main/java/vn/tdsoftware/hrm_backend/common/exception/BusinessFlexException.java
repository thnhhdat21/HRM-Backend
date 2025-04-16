package vn.tdsoftware.hrm_backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class BusinessFlexException extends RuntimeException {
    private int code;

    public BusinessFlexException(int code, String message) {
        super(message);
        this.code = code;
    }
}
