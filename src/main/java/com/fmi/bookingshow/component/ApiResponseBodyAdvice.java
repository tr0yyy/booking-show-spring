package com.fmi.bookingshow.component;

import com.fmi.bookingshow.constants.Apis;
import com.fmi.bookingshow.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        List<String> responseDtoPaths = List.of(Apis.AUTH, Apis.CORE, Apis.ADMIN);
        log.info("Writing to %s".formatted(request.getURI().getPath()));
        for(String path : responseDtoPaths) {
            if (request.getURI().getPath().startsWith(path)) {
                if (body instanceof ResponseDto<?>) {
                    return body;
                }
                return new ResponseDto<>(true, body, null);
            }
        }
        return body;
    }
}