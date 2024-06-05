package com.devops.deploy_app.config.miragesql.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class DbLoggingAspect {

    private static final String TRACKING_TYPE = "QUERY_DB";

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("execution(* com.devops.deploy_app.repository.*.*(..))")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint
     *            join point for advice
     * @return result
     * @throws Throwable
     *             throws IllegalArgumentException
     */

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            long start = System.currentTimeMillis();
            log.debug(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+ ".took="+start);
            try {
                return joinPoint.proceed();
            } catch (IllegalArgumentException e) {
                log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
                throw e;
            } finally {
                long finish = System.currentTimeMillis();
                long took = finish - start;
                log.debug(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+ ".took="+took);
            }
        } else {
            return joinPoint.proceed();
        }
    }
}
