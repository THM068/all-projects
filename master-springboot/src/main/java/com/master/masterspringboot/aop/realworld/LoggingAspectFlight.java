package com.master.masterspringboot.aop.realworld;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspectFlight {

    private Logger logger = Logger.getLogger(LoggingAspectFlight.class.getName());

    @Before("execution(public String com.master.masterspringboot.aop.realworld.Flight.getId())")
    public void logging() {
        logger.info("Flight getId about to be called ....");
    }
}
