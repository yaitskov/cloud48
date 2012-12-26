package org.cc.ctl;

import org.cc.dao.CloudRequestDao;
import org.cc.ent.*;
import org.cc.exception.CloudException;
import org.cc.exception.QueueFullException;
import org.cc.response.CloudErrorResponse;
import org.cc.response.CloudInvalidArgsResponse;
import org.cc.service.RequestQueueService;
import org.cc.util.LogUtil;
import org.cc.util.SecurityUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;


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

    @Resource
    private RequestQueueService requestQueue;

    /**
     * Starts a process of creation new VM.
     * It's asynchronous method.
     *
     * @return request id.
     */
    @ResponseBody
    @RequestMapping("/create")
    @Transactional(rollbackFor = Throwable.class)
    public int create(@Valid NewVmSpec vmSpec) throws QueueFullException {
        CreateVmRequest request = new CreateVmRequest();
        // todo: validate type Enum or String?
        request.setSpec(vmSpec);
        requestQueue.enqueue(request);
        logger.debug("/container/create {} => {}", vmSpec, request.getId());
        return request.getId();
    }

    /**
     * Adds to queue dummy asynchronous command that can sleep specified
     * amount of milliseconds.
     * @return request id
     */
    @ResponseBody
    @RequestMapping("/nop")
    @Transactional(rollbackFor = Throwable.class)
    public int noOperation(@Valid Delay delay) throws QueueFullException {
        Nop nop = new Nop();
        nop.setDelay(delay);
        requestQueue.enqueue(nop);
        logger.debug("/container/nop {} => request id {}", delay, nop.getId());
        return nop.getId();
    }
}
