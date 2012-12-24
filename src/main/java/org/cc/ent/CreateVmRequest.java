package org.cc.ent;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * Initial request for creation new
 *
 * Daneel Yaitskov
 */
@Entity
@DiscriminatorValue("create-vm")
public class CreateVmRequest extends CloudRequest {

    @Embedded
    private NewVmSpec spec;

    public NewVmSpec getSpec() {
        return spec;
    }

    public void setSpec(NewVmSpec spec) {
        this.spec = spec;
    }
}