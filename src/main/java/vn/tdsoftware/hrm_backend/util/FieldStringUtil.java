package vn.tdsoftware.hrm_backend.util;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;

public class FieldStringUtil {
    public static void validatorNameAndCode(String name, String code) {
        if (name.trim().isEmpty() || code.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_AND_CODE_IS_NULL);
        }
    }

    public static void validatorPass( String password) {
        if (password.trim().length() < 8) {
            throw new BusinessException(ErrorCode.USERNAME_OR_PASSWORD_IS_EXIST);
        }
    }
}
