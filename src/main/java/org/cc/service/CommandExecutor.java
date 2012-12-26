package org.cc.service;

import org.cc.dao.CloudRequestDao;
import org.cc.ent.persistent.CloudRequest;
import org.cc.ent.RequestStatus;
import org.cc.exception.CloudException;
import org.cc.props.DynaIntPro;
import org.cc.util.LogUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Daneel Yaitskov
 */
@Service
public class CommandExecutor {

    private static final Logger logger = LogUtil.get();

    @Resource(name = "commandQueue")
    private BlockingQueue<Integer> commandQueue;

    @Resource(name = "tpool.command.size")
    private DynaIntPro numThreads;

    @Resource
    private CloudRequestDao requestDao;

    private List<Thread> threads = new ArrayList<Thread>();

    @PostConstruct
    public void init() {
        logger.info("starting {} command executor threads", numThreads.get());
        for (int i = 0; i < numThreads.get(); ++i) {
            addThread(i);
        }
    }

    private void addThread(final int n) {
        Thread t = new Thread() {
            @Override
            public void run() {
                logger.info("command executor thread {} started", n);
                try {
                    mainLoop();
                } catch (InterruptedException e) {
                    logger.debug("command executor thread was interrupted. end.");
                    return;
                }
            }
        };
        t.setDaemon(true);
        t.start();
        threads.add(t);
    }

    public void mainLoop() throws InterruptedException {
        while (true) {
            Integer cmdId = commandQueue.take();
            logger.debug("took command {}", cmdId);
            try {
                loadAndExecute(cmdId);
            } catch (CloudException e) {
                handleException(e, cmdId);
            } catch (Throwable e) {
                handleException(e, cmdId);
            }
        }
    }

    @Transactional
    public void handleException(Throwable e, Integer cmdId) {
        logger.error("remove require {} due exception", e);
        requestDao.removeById(cmdId);
    }

    @Transactional
    public void loadAndExecute(Integer cmdId) throws CloudException {
        CloudRequest request = requestDao.find(cmdId);
        if (request == null) {
            logger.error("request {} isn't found in DB. skip.", cmdId);
            return;
        }
        logger.debug("request {} is {}", cmdId, request);
        request.execute();
        if (request.getStatus() == RequestStatus.OK) {
            requestDao.remove(request);
            logger.debug("request {} is finished. remove it", request.getId());
        }
    }
}
