package com.fmi.bookingshow.microservices;

import com.netflix.discovery.EurekaClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class GenericService {
    @Autowired
    protected Retry retry;

    @Autowired
    protected CircuitBreaker circuitBreaker;

    @Autowired
    protected EurekaClient discoveryClient;
}
