package com.master.masterspringboot.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ExceptionAspect {

    private Logger logger = Logger.getLogger(ExceptionAspect.class.getName());

    @AfterThrowing(pointcut = "execution(* com.master.masterspringboot.aop.SampleAdder.logMe(..))", throwing = "runtimeException")
    public void processingException(RuntimeException runtimeException) throws Throwable {
        System.out.println("**************************");
        logger.severe(runtimeException.getMessage());
    }
}
