
package org.robbins.flashcards.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Aspect
public class ExecutionTimeLogger {

    static final Logger logger = LoggerFactory.getLogger(ExecutionTimeLogger.class);

    @Around("execution(* org.robbins.flashcards..*.*(..))")
    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final Object retVal = joinPoint.proceed();

        stopWatch.stop();

        final StringBuffer logMessage = new StringBuffer();
        logMessage.append(joinPoint.getSignature().getDeclaringType().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());

        logMessage.append("(");

        // append args
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logMessage.append(arg).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }

        logMessage.append(")");

        logMessage.append(" execution time: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");
        logger.debug(logMessage.toString());
        return retVal;
    }
}
