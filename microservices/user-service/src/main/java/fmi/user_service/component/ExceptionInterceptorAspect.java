package fmi.user_service.component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ExceptionInterceptorAspect {
    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object interceptAndStoreExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();

            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                throw ex; // safety first, even if it should not happen, since it's around a restcontroller annotation
            }
            requestAttributes.setAttribute("exceptionTypes", method.getExceptionTypes(), RequestAttributes.SCOPE_REQUEST);
            throw ex; // produce a faulty request
        }
    }
}
