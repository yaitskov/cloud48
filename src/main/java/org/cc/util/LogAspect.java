package org.cc.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;

/**
 * Daneel Yaitskov
 */
@Aspect
public class LogAspect {

    private static final Logger logger = LogUtil.get();

    @AfterReturning(
            pointcut = "execution(public * *(..)) && @annotation(TraceArgsReturn)",
            returning = "result"
    )
    public void logArgsAndReturnValue(JoinPoint pjp, Object result) {
        logger.debug("method {} => {}", pjp.getSignature().getName(), result);
    }
}
