package org.cc.ent.persistent;

import org.cc.dao.ContainerServiceDao;
import org.cc.ent.arg.NewVmSpec;
import org.cc.ent.persistent.CloudRequest;
import org.cc.exception.CloudException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

/**
 * Initial request for creation new
 *
 * Daneel Yaitskov
 */
@Configurable
@Entity
@DiscriminatorValue("create-vm")
public class CreateVmRequest extends CloudRequest {

    @Embedded
    private NewVmSpec spec;

    @Resource
    private ContainerServiceDao contSrvDao;

    public NewVmSpec getSpec() {
        return spec;
    }

    public void setSpec(NewVmSpec spec) {
        this.spec = spec;
    }


    @Transactional
    @Override
    public void execute() throws CloudException {

        // get proper container manager
        ContainerServiceEnt cntSrv = contSrvDao.findByContainerType(spec.getType());
        // send command him
        cntSrv.create(spec, this.getId());

    }
}
