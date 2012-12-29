package org.cc.dao;

import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import org.cc.ent.persistent.ContainerServiceEnt;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Daneel Yaitskov
 */
@Repository
public class ContainerServiceDao extends Dao<ContainerServiceEnt> {


    public ContainerServiceEnt findByContainerType(String ctName) {
        Query q = em().createQuery("select cs from ContainerType ct inner join ct.contService as cs " +
                "where ct.name = :ctname");
        q.setParameter("ctname", ctName);
        return (ContainerServiceEnt)q.getSingleResult();
    }
}
