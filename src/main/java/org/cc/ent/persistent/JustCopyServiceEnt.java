package org.cc.ent.persistent;

import org.cc.ent.arg.NewVmSpec;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Daneel Yaitskov
 */
@Configurable
@Entity
@DiscriminatorValue("just-copy")
public class JustCopyServiceEnt extends ContainerServiceEnt {

    @Column


    /**
     *
     * @param spec
     */
    @Override
    public void create(NewVmSpec spec) {
        super.create(spec);
    }
}
