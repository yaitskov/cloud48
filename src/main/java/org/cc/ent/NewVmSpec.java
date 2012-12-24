package org.cc.ent;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Describes critical parameters of new VM.
 *
 * Daneel Yaitskov
 */
@Embeddable
public class NewVmSpec {

    /**
     * Name of base template of CIS.
     */
    @Column
    @NotNull
    @Length(min = 2, max = 200)
    private String type;
    /**
     * Max memory in megabytes.
     */
    @Column
    @Min(32)
    @Max(1024*1024)
    private int memory;
    /**
     * Number CPU cores in VM.
     */
    @Column
    @Min(1)
    @Max(256)
    private int core;

    /**
     * Frequency in MHz one for all cores
     */
    @Column
    @Min(1)
    @Max(8000)
    private int frequency;
    /**
     * Max disk size in gigabytes
     */
    @Column
    @Min(1)
    @Max(1024*1024)
    private int disk;

    /**
     * Max network throughput in megabytes per second
     */
    @Column
    @Min(1)
    @Max(1000)
    private int network;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }
}
