package org.cc.ctl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    /**
     * Starts a process of creation new VM.
     * It's asynchronous method.
     *
     * @param type      name of base template of VM (software inside)
     * @param memory    maximum required memory in megabytes
     * @param core      number cpu cores in VM.
     * @param frequency max frequency of one cpu core in VM
     * @param disk      max disk size  in gigabytes
     * @param network   max network throughput in megabytes per second
     * @return request id.
     */
    @ResponseBody
    @RequestMapping("/create")
    public int create(@RequestParam("type") String type,
                      @RequestParam("memory") int memory,
                      @RequestParam("core") int core,
                      @RequestParam("frequency") int frequency,
                      @RequestParam("disk") int disk,
                      @RequestParam("network") int network) {
        return 0;
    }
}
