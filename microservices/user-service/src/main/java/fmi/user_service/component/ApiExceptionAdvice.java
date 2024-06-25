package fmi.user_service.component;


import fmi.user_service.dto.response.ResponseDto;
import fmi.user_service.exceptions.ErrorMessage;
import fmi.user_service.utils.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestControllerAdvice
@Slf4j
public class ApiExceptionAdvice {
    private final int logLines = 10;
    private final List<Class<?>> globalExceptionTypes = List.of(
            HttpRequestMethodNotSupportedException.class,
            ExpiredJwtException.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handleException(Exception ex) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return this.respondWithUnexpectedError(ex);
        }

        Class<?>[] exceptionTypes = (Class<?>[]) requestAttributes.getAttribute("exceptionTypes", RequestAttributes.SCOPE_REQUEST);
        Class<?>[] exceptionTypesToValidate;

        if (exceptionTypes == null) {
            exceptionTypesToValidate = globalExceptionTypes.toArray(Class[]::new);
        } else {
            exceptionTypesToValidate =
                    Stream.concat(Arrays.stream(exceptionTypes), globalExceptionTypes.stream())
                            .distinct()
                            .toArray(Class[]::new);
        }

        for (Class<?> exception : exceptionTypesToValidate) {
            if (exception.isInstance(ex)) {
                return this.respondWithExpectedError(ex);
            }
        }
        return this.respondWithUnexpectedError(ex);
    }

    private ResponseEntity<ResponseDto<String>> respondWithExpectedError(Exception exception) {
        log.error("Expected error occurred " + exception.getClass().getSimpleName() + " " + exception.getMessage() +
                "\nStacktrace:" + StringUtils.getFirstNLinesOfStacktrace(exception.getStackTrace(), logLines) +
                "\n+ more " + (exception.getStackTrace().length - logLines) + " lines");
        return ResponseEntity.badRequest().body(new ResponseDto<>(exception.getMessage()));
    }

    private ResponseEntity<ResponseDto<String>> respondWithUnexpectedError(Exception exception) {
        log.error("Unexpected error occurred " + exception.getClass().getSimpleName() + " " + exception.getMessage() +
                "\nStacktrace:" + StringUtils.getFirstNLinesOfStacktrace(exception.getStackTrace(), logLines) +
                "\n+ more " + (exception.getStackTrace().length - logLines) + " lines");
        return ResponseEntity.badRequest().body(new ResponseDto<>(ErrorMessage.INTERNAL_SERVER_ERROR));
    }
}
