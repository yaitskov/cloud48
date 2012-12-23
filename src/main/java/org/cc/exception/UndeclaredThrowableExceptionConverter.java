package org.cc.exception;

import org.cc.response.CloudErrorResponse;
import org.cc.util.LogUtil;
import org.slf4j.Logger;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * This class handles {@link Throwable} exceptions
 * that cannot be caught with matching as Throwable.
 * Daneel Yaitskov
 */
public class UndeclaredThrowableExceptionConverter extends ExceptionConverter {

    private static final Logger logger = LogUtil.get();

    /**
     * Extracts undeclared exception and converts it to the response object.
     * @param e exception containing Throwable one
     * @return object for conversion
     */
    @Override
    public Object convert(Exception e) {
        UndeclaredThrowableException unThEx = (UndeclaredThrowableException)e;
        logger.error("convert to json UndeclaredThrowableException",
                unThEx.getUndeclaredThrowable());
        return new CloudErrorResponse(unThEx.getUndeclaredThrowable());
    }
}
