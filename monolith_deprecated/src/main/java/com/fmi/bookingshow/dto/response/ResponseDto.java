package com.fmi.bookingshow.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDto<T> {
    public boolean success;
    public T data;
    public String error;

    public ResponseDto (boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ResponseDto (String error) {
        this.success = false;
        this.error = error;
    }

    public ResponseDto () {}

    @JsonCreator
    public ResponseDto(@JsonProperty("success") boolean success, @JsonProperty("data") T data, @JsonProperty("error") String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }
}
