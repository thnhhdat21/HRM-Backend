package vn.tdsoftware.hrm_backend.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseError;
import vn.tdsoftware.hrm_backend.common.enums.ResponseCode;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseError> handlingException(Exception exception, WebRequest request) {
        ResponseError response = new ResponseError();
        response.setTimestamp(LocalDateTime.now());
        response.setSuccess(false);
        response.setPath(request.getDescription(false).replace("uri=", ""));
        String message = exception.getMessage();

        if (exception instanceof BusinessException) {
            ErrorCode errorCode = ((BusinessException) exception).getErrorCode();
            response.setCode(String.valueOf(errorCode.getCode()));
            response.setMessage(message);
            log.error("BusinessException", exception);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (exception instanceof BusinessFlexException ex) {
            response.setCode(String.valueOf(ex.getCode()));
            response.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (exception instanceof SQLException) {
            response.setCode(ResponseCode.SQL_ERROR.getCode());
            response.setMessage(message);
            log.error("SQL Exception", exception);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            BindingResult bindingResult = ex.getBindingResult();
            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            response.setMessage(errorMessages);
            response.setCode(ResponseCode.BUSINESS_ERROR.getCode());
            log.info("MethodArgumentNotValidException: {}", message);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        } else if (exception instanceof ConstraintViolationException) {
            message = message.substring(message.indexOf(" ") + 1);
            response.setMessage(message);
            response.setCode(ResponseCode.BUSINESS_ERROR.getCode());
            response.setError("PathVariable Invalid");
            log.info("ConstraintViolationException: {}", message);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
//        } else if (exception instanceof UsernameNotFoundException) {
//            response.setMessage("Invalid username");
//            response.setCode(ResponseCode.BAD_CREDENTIALS.getCode());
//            response.setError(ResponseCode.BAD_CREDENTIALS.getMessage());
//            log.info("Invalid UserName");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(response);
//        } else if (exception instanceof BadCredentialsException) {
//            response.setMessage("Invalid password");
//            response.setCode(ResponseCode.BAD_CREDENTIALS.getCode());
//            response.setError(ResponseCode.BAD_CREDENTIALS.getMessage());
//            log.info("Invalid password");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(response);
        }else {
            response.setMessage(exception.getMessage());
            response.setCode(ResponseCode.GENERAL_EXCEPTION.getCode());
            response.setError(ResponseCode.GENERAL_EXCEPTION.getMessage());
            log.error("GeneralException", exception);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }
    }
}
