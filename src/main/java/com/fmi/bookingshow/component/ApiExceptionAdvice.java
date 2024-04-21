package com.fmi.bookingshow.component;

import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.exceptions.ErrorMessage;
import com.fmi.bookingshow.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@RestControllerAdvice
@Slf4j
public class ApiExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handleException(Exception ex) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return this.respondWithInternalServerError(ex);
        }
        Class<?>[] exceptionTypes = (Class<?>[]) requestAttributes.getAttribute("exceptionTypes", RequestAttributes.SCOPE_REQUEST);
        if (exceptionTypes != null) {
            for (Class<?> exception : exceptionTypes) {
                if (exception.isInstance(ex)) {
                    return this.respondWithExpectedError(ex);
                }
            }
        }
        return this.respondWithUnexpectedError(ex);
    }

    private ResponseEntity<ResponseDto<String>> respondWithInternalServerError(Exception exception) {
        log.error("%s - %s".formatted(ErrorMessage.INTERNAL_SERVER_ERROR, exception));
        return ResponseEntity.badRequest().body(new ResponseDto<>(ErrorMessage.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<ResponseDto<String>> respondWithExpectedError(Exception exception) {
        int logLines = 10;
        log.error("Expected error occurred " + exception.getMessage() +
                "\nStacktrace:" + StringUtils.getFirstNLinesOfStacktrace(exception.getStackTrace(), logLines) +
                "\n+ more " + (exception.getStackTrace().length - logLines) + " lines");
        return ResponseEntity.badRequest().body(new ResponseDto<>(exception.getMessage()));
    }

    private ResponseEntity<ResponseDto<String>> respondWithUnexpectedError(Exception exception) {
        log.error("Unexpected error occurred " + StringUtils.joinElementsFromArray(exception.getStackTrace()));
        return ResponseEntity.badRequest().body(new ResponseDto<>(ErrorMessage.INTERNAL_SERVER_ERROR));
    }
}
