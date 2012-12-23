package org.cc.unit.exception;

import mockit.Expectations;
import mockit.NonStrict;
import mockit.NonStrictExpectations;
import org.cc.exception.CloudExceptionHandler;
import org.cc.exception.JsonView;
import org.cc.exception.ValueClassTakesException;
import org.cc.response.CloudErrorResponse;
import org.cc.response.CloudResponse;
import org.cc.util.LogUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.SocketException;
import java.security.AccessControlException;

/**
 * Daneel Yaitskov
 */
public class CloudExceptionHandlerTest {

    CloudExceptionHandler tested;
    HttpServletRequest req;
    HttpServletResponse res;
    
        
    @NonStrict
    Logger logger;

    @Before
    public void setUp() {
        // stub big stack trace of fake exceptions
        new NonStrictExpectations() {
            LogUtil lu;
            {
                LogUtil.get();
                result = logger;
            }
        };
        tested = new CloudExceptionHandler();
        ValueClassTakesException exRes = new ValueClassTakesException();
        exRes.put(IllegalArgumentException.class, CloudErrorResponse.class);
        exRes.put(RuntimeException.class, CloudErrorResponse.class);
        exRes.put(SocketException.class, IllegalArgumentException.class);
        tested.setExceptionResponse(exRes);
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        tested.init();
    }

    @Test
    public void testResolveException() {
        ModelAndView mav = tested.resolveException(req, res, null, new NumberFormatException());
        Assert.assertEquals(CloudErrorResponse.class,
                mav.getModel().get(JsonView.DATA_FIELD_NAME).getClass());

        mav = tested.resolveException(req, res, null, new SocketException());
        Assert.assertEquals(IllegalArgumentException.class,
                mav.getModel().get(JsonView.DATA_FIELD_NAME).getClass());

        mav = tested.resolveException(req, res, null, new Exception());
        Assert.assertNull(mav);

        mav = tested.resolveException(req, res, null,
                new UndeclaredThrowableException(new AccessControlException("hel34")));
        Assert.assertEquals(CloudErrorResponse.class,
                mav.getModel().get(JsonView.DATA_FIELD_NAME).getClass());

        Assert.assertEquals("hel34",
                ((CloudErrorResponse)mav.getModel().get(JsonView.DATA_FIELD_NAME)).getMessage());
    }
}
