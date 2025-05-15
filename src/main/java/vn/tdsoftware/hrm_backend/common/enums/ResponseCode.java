package vn.tdsoftware.hrm_backend.common.enums;


import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("00", "Success"),
    BUSINESS_ERROR("01", "Business Error"),
    SQL_ERROR("02", "SQL Error"),
    TOKEN_ERROR("10", "Token Error"),
    BAD_CREDENTIALS("11", "Invalid Username or Password"),
    TOKEN_EXPIRATION("12", "Token expired"),
    NO_AUTHORIZATION("13", "Không có quyền truy cập"),
    GENERAL_EXCEPTION("99", "Unknown error"),
    ;
    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
