package com.trip.back.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@org.aspectj.lang.annotation.Aspect 
@Component
@Slf4j
public class Aspect{
	
	
    @Around("@annotation(LogExecutionTime)")
    public Object executionAspect(ProceedingJoinPoint joinPoint) throws Throwable{   
        StopWatch stopWatch = new StopWatch();
        try {
        	stopWatch.start();
        	return joinPoint.proceed();
        }finally {
			stopWatch.stop();
			 log.info("{} - total time = {}s",stopWatch.prettyPrint());

		}
    }
}