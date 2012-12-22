package org.cc;

import org.cc.command.ContainerCommand;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * Controls separated VM over VS hypervisor or CIS.
 * Daneel Yaitskov
 */
@Service
public class ContainerManager {

    /**
     *
     * @param cmd
     */
    public void run(ContainerCommand cmd) {

    }

    public List<Container> findAll() {
        return null;
    }





}
