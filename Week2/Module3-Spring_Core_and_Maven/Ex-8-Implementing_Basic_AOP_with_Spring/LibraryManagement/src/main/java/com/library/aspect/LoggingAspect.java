package com.library.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.library.service.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore() {
        System.out.println("LoggingAspect.logBefore() : Method is about to execute");
    }

    @After("serviceMethods()")
    public void logAfter() {
        System.out.println("LoggingAspect.logAfter() : Method has executed");
    }
}
