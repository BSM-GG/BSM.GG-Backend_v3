package bsmgg.bsmgg_backend.global.error;

import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import bsmgg.bsmgg_backend.global.error.exception.GradException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GradException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(GradException error) {
        final ErrorCode errorCode = error.getErrorCode();
        log.error(ErrorResponse.errorLogsFormat.formatted(errorCode.getStatus(), errorCode.getMessage()));
        return new ResponseEntity<>(ErrorResponse.of(errorCode), HttpStatusCode.valueOf(errorCode.getStatus()));
    }
}
