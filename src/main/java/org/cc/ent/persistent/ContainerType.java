package org.cc.ent.persistent;

import javax.persistence.*;

/**
 * Daneel Yaitskov
 */
@Entity
@Table(name = "cnt_type")
public class ContainerType extends AbstractEntity {

    /**
     * Format "container service" - "container type" - "container version"
     */
    @Column(name = "ctname")
    String name;


    @JoinColumn(name = "ctservice")
    @ManyToOne
    ContainerServiceEnt contService;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContainerServiceEnt getContService() {
        return contService;
    }

    public void setContService(ContainerServiceEnt contService) {
        this.contService = contService;
    }
}
