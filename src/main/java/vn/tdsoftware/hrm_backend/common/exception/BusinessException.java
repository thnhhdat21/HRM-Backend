package vn.tdsoftware.hrm_backend.common.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;

@Setter
@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException{

public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());

    this.errorCode = errorCode;
}

    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
