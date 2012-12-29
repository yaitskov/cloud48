package org.cc.ent.persistent;

import org.cc.ent.arg.NewVmSpec;

import javax.persistence.*;

/**
 *
 * There can be multiple different types of container services.
 *
 * For example for speeding application developing itself
 * JustCopyContainerService - manager creates container by copying template
 *
 * tomcat, allocation tcp ports for it and appending name to /etc/hosts.
 *
 * container service type follows from container type.
 *
 *
 * container service object is 1 instance per type
 *
 *
 *
 *
 * Daneel Yaitskov
 */

@Entity
@Table(name = "t_cloud_request")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "cmd", discriminatorType = DiscriminatorType.STRING)
public class ContainerServiceEnt extends AbstractEntity {


    public void create(NewVmSpec spec, int requestId) {

    }
}
