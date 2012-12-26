package org.cc.service;

import org.cc.dao.CloudRequestDao;
import org.cc.ent.persistent.CloudRequest;
import org.cc.ent.RequestStatus;
import org.cc.ent.persistent.User;
import org.cc.exception.QueueFullException;
import org.cc.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

/**
 * Daneel Yaitskov
 */
@Service
public class RequestQueueService {

    @Resource
    private CloudRequestDao requestDao;

    /**
     * Queue of primary keys of cloud request table.
     * I could place command ids into JMS queue with
     * XA transaction it's COOL but it's slow.
     * <p/>
     * This local queue should be replaced with Hazelcast queue.
     */
    @Resource(name = "commandQueue")
    private BlockingQueue<Integer> requestQueue;


    @Transactional(
            propagation = Propagation.MANDATORY,
            rollbackFor = Throwable.class)
    public int enqueue(CloudRequest request) throws QueueFullException {
        setRequestBaseFields(request);
        requestDao.save(request);
        if (requestQueue.offer(request.getId())) {
            return request.getId();
        }
        throw new QueueFullException();
    }

    public void setRequestBaseFields(CloudRequest request) {
        User user = SecurityUtil.getCurrent();
        request.setAuthor(user);
        request.setStatus(RequestStatus.IN_QUEUE);
        request.setCreated(new Date());
    }
}
