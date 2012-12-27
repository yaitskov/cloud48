package org.cc.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

/**
 * Daneel Yaitskov
 */
@Aspect
public class LogAspect {

    private static final Logger logger = LogUtil.get();

    /**
     * ((TraceArgsReturn)((MethodSignature)pjp.getSignature()).getMethod().getAnnotations()[0]).level()
     * @param jp
     * @param result
     */
    @AfterReturning(
            pointcut = "execution(public * *(..)) && @annotation(TraceReturnOnly)",
            returning = "result"
    )
    public void logReturnValue(JoinPoint jp, Object result) {
        MethodSignature ms = (MethodSignature)jp.getSignature();
        TraceReturnOnly ann = ms.getMethod().getAnnotation(TraceReturnOnly.class);
        ann.level().logReturn(ms, jp, result);
    }

    @AfterReturning(
            pointcut = "execution(public * *(..)) && @annotation(TraceArgsReturn)",
            returning = "result"
    )
    public void logArgsAndReturnValue(JoinPoint jp, Object result) {
        MethodSignature ms = (MethodSignature)jp.getSignature();
        TraceArgsReturn ann = ms.getMethod().getAnnotation(TraceArgsReturn.class);
        ann.level().logArgsReturn(ms, jp, result);
    }


    @Before("execution(public * *(..)) && @annotation(TraceArgsOnly)")
    public void logArgsOnly(JoinPoint jp) {
        MethodSignature ms = (MethodSignature)jp.getSignature();
        TraceArgsOnly ann = ms.getMethod().getAnnotation(TraceArgsOnly.class);
        ann.level().logArgs(ms, jp);
    }

    @AfterThrowing(
            pointcut = "execution(public * *(..)) && @annotation(TraceArgsReturn)",
            throwing = "result"
    )
    public void logArgsAndException(JoinPoint jp, Throwable result) {
        MethodSignature ms = (MethodSignature)jp.getSignature();
        TraceArgsReturn ann = ms.getMethod().getAnnotation(TraceArgsReturn.class);
        ann.level().logArgsException(ms, jp, result);
    }

    @AfterThrowing(
            pointcut = "execution(public * *(..)) && @annotation(TraceReturnOnly)",
            throwing = "result"
    )
    public void logException(JoinPoint jp, Throwable result) {
        MethodSignature ms = (MethodSignature)jp.getSignature();
        TraceArgsReturn ann = ms.getMethod().getAnnotation(TraceArgsReturn.class);
        ann.level().logException(ms, jp, result);
    }


}
