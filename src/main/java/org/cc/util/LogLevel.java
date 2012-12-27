package org.cc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

/**
 * Daneel Yaitskov
 */
public enum LogLevel {


    EXCEPTION {
    }, // log only in exception case as error
    INFO {

        @Override
        public void logArgs(MethodSignature ms, JoinPoint jp) {
            logger.info(
                    String.format("args of %s#%s: %s",
                            jp.getThis().getClass().getSimpleName(),
                            ms.getName(),
                            joinArgs(jp.getArgs())));
        }

        @Override
        public void logArgsReturn(MethodSignature ms, JoinPoint jp, Object result) {
            StringBuilder builder = new StringBuilder();
            objToString(builder, result);
            logger.info(
                    String.format("args of %s#%s: %s; returned %s",
                            jp.getThis().getClass().getSimpleName(),
                            ms.getName(),
                            joinArgs(jp.getArgs()),
                            builder.toString()));
        }

        @Override
        public void logReturn(MethodSignature ms, JoinPoint jp, Object result) {
            StringBuilder builder = new StringBuilder();
            objToString(builder, result);
            logger.info(
                    String.format("%s#%s returned %s",
                            jp.getThis().getClass().getSimpleName(),
                            ms.getName(),
                            builder.toString()));
        }
    },      // log as info message
    DEBUG {

    };

    private static final Logger logger;
    private static final ObjectMapper omapper;

    static {
        logger = LogUtil.get();
        omapper = new ObjectMapper();
    }

    public void logArgs(MethodSignature ms, JoinPoint jp) {}

    public void logArgsReturn(MethodSignature ms, JoinPoint jp, Object result) {}

    public void logReturn(MethodSignature ms, JoinPoint jp, Object result) {}

    public void logArgsException(MethodSignature ms, JoinPoint jp, Throwable result) {
        logger.error(
                String.format("%s in %s#%s: %s; args: %s",
                        result.getClass().getSimpleName(),
                        jp.getThis().getClass().getSimpleName(),
                        ms.getName(),
                        result.getMessage(),
                        joinArgs(jp.getArgs())));
    }

    public void logException(MethodSignature ms, JoinPoint jp, Throwable result) {
        logger.error(
                String.format("%s in %s#%s: %s",
                        result.getClass().getSimpleName(),
                        jp.getThis().getClass().getSimpleName(),
                        ms.getClass().getSimpleName(),
                        result.getMessage()));
    }

    private static String joinArgs(Object[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; ) {
            builder.append(i + 1).append(") ");
            objToString(builder, args[i]);
            i += 1;
            if (i < args.length) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private static void objToString(StringBuilder builder, Object obj) {
        if (obj == null) {
            builder.append(obj);
        } else if (obj.getClass().isPrimitive()) {
            builder.append(obj);
        } else {
            builder.append(obj.getClass().getSimpleName()).append(" :: ");
            try {
                builder.append(omapper.writeValueAsString(obj));
            } catch (JsonProcessingException e) {
                builder.append("failed to serialize args for exception cause another exception: ")
                        .append(e.getClass().getSimpleName()).append("/").append(e.getMessage());
            }
        }
    }
}
