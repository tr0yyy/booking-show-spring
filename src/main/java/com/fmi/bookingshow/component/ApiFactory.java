package com.fmi.bookingshow.component;

import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApiFactory {
    @FunctionalInterface
    public interface Callback<T> {
        T onCallback() throws Exception;
    }

    public <T> ResponseEntity<ResponseDto<T>> create(Callback<T> callback) {
        return create(callback, null);
    }

    public <T> ResponseEntity<ResponseDto<T>> create(Callback<T> callback,
                                               Class<? extends Exception> exception) {
        try {
            T data = callback.onCallback();
            return ResponseEntity.ok(new ResponseDto<>(true, data));
        } catch (Exception e) {
            if (exception.isInstance(e)) {
                log.error("Expected error occurred " + StringUtils.joinElementsFromArray(e.getStackTrace()));
                return ResponseEntity.unprocessableEntity().body(new ResponseDto<>(e.getMessage()));
            }
            log.error("Unexpected error occurred " + StringUtils.joinElementsFromArray(e.getStackTrace()));
            return ResponseEntity.internalServerError().body(new ResponseDto<>("Internal server error"));
        }
    }
}
