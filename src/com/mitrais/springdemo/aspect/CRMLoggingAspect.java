package com.mitrais.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
		
	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declarations
	@Pointcut("execution(* com.mitrais.springdemo.controller.*.*(..))")
	private void forControllerPAckage() {}
	
	@Pointcut("execution(* com.mitrais.springdemo.service.*.*(..))")
	private void forServicePAckage() {}
	
	@Pointcut("execution(* com.mitrais.springdemo.dao.*.*(..))")
	private void forDaoPAckage() {}
	
	@Pointcut("forControllerPAckage() || forServicePAckage() || forDaoPAckage()")
	private void forAppFlow() {}
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJointPoint) {
		
		// display method we are calling
		String theMethod = theJointPoint.getSignature().toShortString();
		myLogger.info("=====>> in @Before: calling method: " + theMethod);
		
		// display the arguments to the method
		
		// get the arguments
		Object[] args = theJointPoint.getArgs();
		
		// loop thru 
		for (Object tempArg : args) {
			myLogger.info("=====>> argument: " + tempArg);
		}
	}
	
	// add @AfterReturning advice
	@AfterReturning(
			pointcut = "forAppFlow()",
			returning = "theResult"
			)
	public void afterReturning(JoinPoint theJointPoint, Object theResult) {
		// display method we are returning from
		String theMethod = theJointPoint.getSignature().toShortString();
		myLogger.info("=====>> in @AfterReturning: from method: " + theMethod);
		
		// display data returned
		myLogger.info("=====>> result: " + theResult);
		
	}
}
