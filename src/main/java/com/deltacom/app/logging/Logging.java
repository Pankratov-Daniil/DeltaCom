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
    private static final Logger logger = LogManager.getLogger(Logging.class);

    /**
     * Pointcut for all get methods in services
     */
    @Pointcut("execution(* com.deltacom.app.services.api..get*(..))")
    public void getEntityLogging() {  }

    /**
     * Method for logging get services methods
     * @param pjp proceed join point
     * @return join point proceed result
     */
    @Around("getEntityLogging()")
    public Object aroundGetEntity(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("From " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()) + " returned: " + obj);
        return obj;
    }

    /**
     * Pointcut for all add methods in services
     */
    @Pointcut("execution(* com.deltacom.app.services.api..add*(..))")
    public void addEntityLogging() {  }

    /**
     * Method for logging add services methods
     * @param pjp proceed join point
     * @return join point proceed result
     */
    @Around("addEntityLogging()")
    public Object aroundAddEntity(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " added: " + Arrays.toString(pjp.getArgs()));
        return obj;
    }

    /**
     * Pointcut for all update methods in services
     */
    @Pointcut("execution(* com.deltacom.app.services.api..update*(..))")
    public void updateEntityLogging() {  }

    /**
     * Method for logging update services methods
     * @param pjp proceed join point
     * @return join point proceed result
     */
    @Around("updateEntityLogging()")
    public Object aroundUpdateEntity(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " updated: " + Arrays.toString(pjp.getArgs()));
        return obj;
    }

    /**
     * Pointcut for all delete methods in services
     */
    @Pointcut("execution(* com.deltacom.app.services.api..delete*(..))")
    public void deleteEntityLogging() {  }

    /**
     * Method for logging delete services methods
     * @param pjp proceed join point
     * @return join point proceed result
     */
    @Around("deleteEntityLogging()")
    public Object aroundDeleteEntity(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " deleted: " + Arrays.toString(pjp.getArgs()));
        return obj;
    }

    /**
     * Pointcut for block contract method in ContractService
     */
    @Pointcut("execution(* com.deltacom.app.services.api.ContractService.blockContract(..))")
    public void blockContractLogging() {  }

    /**
     * Method for logging block contract method
     * @param pjp proceed join point
     * @return join point proceed result
     */
    @Around("blockContractLogging()")
    public Object aroundBlockContract(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " blocked contract: " + Arrays.toString(pjp.getArgs()));
        return obj;
    }

    /**
     * Method for logging exception
     * @param jp join point
     * @param exception raised exception
     */
    @AfterThrowing(value = "execution(* com.deltacom.app.services..*(..))", throwing = "exception")
    public void exceptionLogging(JoinPoint jp, Exception exception) {
        logger.error("Exception raised in method: " + jp.toShortString() + " with args: " + Arrays.toString(jp.getArgs()) +
        ". Exception: " + exception);
    }
}
