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
     * Method for logging send services methods
     * @param pjp proceed join point
     * @return join point proceed result
     */
    @Around("execution(* com.deltacom.app.services.api..send*(..))")
    public Object aroundSendServices(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Invoked " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()));
        Object obj = pjp.proceed();
        logger.info("Method " + pjp.toShortString() + " with args: " + Arrays.toString(pjp.getArgs()) + " sent message.");
        return obj;
    }

    /**
     * Method for logging exception
     * @param jp join point
     * @param exception raised exception
     */
    @AfterThrowing(value = "execution(* com.deltacom.app.services..*(..))", throwing = "exception")
    public void exceptionLogging(JoinPoint jp, Exception exception) {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("Exception raised in method: ");
        stringBuilder.append(jp.toShortString());
        stringBuilder.append(" with args: ");
        stringBuilder.append(Arrays.toString(jp.getArgs()));
        stringBuilder.append(". Exception: ");
        stringBuilder.append(exception);
        stringBuilder.append(". StackTrace: ");
        for(StackTraceElement element : exception.getStackTrace()) {
            stringBuilder.append("\n\t");
            stringBuilder.append(element);
        }
        logger.error(stringBuilder.toString());
    }
}
