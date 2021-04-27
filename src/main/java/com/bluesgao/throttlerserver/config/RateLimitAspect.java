package com.bluesgao.throttlerserver.config;

import com.bluesgao.throttlerserver.anno.RateLimit;
import com.bluesgao.throttlerserver.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Slf4j
@Configuration
@Aspect
public class RateLimitAspect {
    @Autowired
    private RateLimitService rateLimitService;

    //执行redis的具体方法，限制method,保证没有其他的东西进来
    @Around("execution(* com.bluesgao.throttlerserver.controller ..*(..) )")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();

        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        if (rateLimit != null) {
            if (rateLimitService.throttle("rateLimit:", rateLimit)) {
                log.info("限流时间段内访问");
                return joinPoint.proceed();
            }
        } else {
            return joinPoint.proceed();
        }
        log.error("已经到设置限流次数");
        throw new RuntimeException("已经到设置限流次数");
    }

}
