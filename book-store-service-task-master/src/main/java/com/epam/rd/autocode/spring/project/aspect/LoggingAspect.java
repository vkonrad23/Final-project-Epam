package com.epam.rd.autocode.spring.project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.epam.rd.autocode.spring.project.service.impl..*(..))")
    public void logServiceCall(JoinPoint joinPoint) {
        log.debug("Service call: {} with {} args", joinPoint.getSignature().toShortString(), joinPoint.getArgs().length);
    }

    @AfterThrowing(pointcut = "execution(* com.epam.rd.autocode.spring.project..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("Error in {}: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}
