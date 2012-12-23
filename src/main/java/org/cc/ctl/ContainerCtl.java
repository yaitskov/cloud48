package org.cc.ctl;

import org.cc.ent.NewVmSpec;
import org.cc.exception.CloudException;
import org.cc.response.CloudErrorResponse;
import org.cc.response.CloudInvalidArgsResponse;
import org.cc.util.LogUtil;
import org.cc.util.SecurityUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;


/**
 * Takes request from web clients, convert scalar parameters
 * to loaded objects, checks permissions, validates and
 * puts request to the queue.
 * <p/>
 * <p/>
 * Daneel Yaitskov
 */
@Controller
@RequestMapping("/container")
public class ContainerCtl {

    private static final Logger logger = LogUtil.get();

    /**
     * Starts a process of creation new VM.
     * It's asynchronous method.
     *
     * @return request id.
     */
    @ResponseBody
    @RequestMapping("/create")
    public int create(@Valid NewVmSpec vmSpec) {
        logger.debug("current user {}", SecurityUtil.getCurrent().getLogin());
        return vmSpec.getCore();
    }
}
