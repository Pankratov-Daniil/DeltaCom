package com.deltacom.app.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * Class for logging using AspectJ
 */
@Aspect
public class Logging {
    private final static Logger logger = LogManager.getLogger(Logging.class);

    @Pointcut("execution(* com.deltacom.app.services.api..get*(..))")
    public void getEntityLogging() {  }

    @Around("getEntityLogging()")
    public Object aroundGetEntity(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("From " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()) + " returned: " + obj);
        return obj;
    }

    @Pointcut("execution(* com.deltacom.app.services.api..add*(..))")
    public void addEntityLogging() {  }

    @Around("addEntityLogging()")
    public Object aroundAddEntity(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " added: " + Arrays.toString(pjp.getArgs()));
        return obj;
    }

    @Pointcut("execution(* com.deltacom.app.services.api..update*(..))")
    public void updateEntityLogging() {  }

    @Around("updateEntityLogging()")
    public Object aroundUpdateEntity(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " updated: " + Arrays.toString(pjp.getArgs()));
        return obj;
    }

    @Pointcut("execution(* com.deltacom.app.services.api..delete*(..))")
    public void deleteEntityLogging() {  }

    @Around("deleteEntityLogging()")
    public Object aroundDeleteEntity(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " deleted: " + Arrays.toString(pjp.getArgs()));
        return obj;
    }

    @Pointcut("execution(* com.deltacom.app.services.api.ContractService.blockContract(..))")
    public void blockContractLogging() {  }

    @Around("blockContractLogging()")
    public Object aroundBlockContract(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " blocked contract: " + Arrays.toString(pjp.getArgs()));
        return obj;
    }

    @AfterThrowing(value = "execution(* com.deltacom.app.services..*(..))", throwing = "exception")
    public void exceptionLogging(JoinPoint jp, Exception exception) {
        logger.error("Exception raised in method: " + jp.toShortString() + " with args: " + Arrays.toString(jp.getArgs()) +
        ". Exception: " + exception);
    }
}
