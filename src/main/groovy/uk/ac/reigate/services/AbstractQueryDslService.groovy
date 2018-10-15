package uk.ac.reigate.services

import javax.persistence.EntityManager

import org.springframework.beans.factory.annotation.Autowired

import com.querydsl.core.types.EntityPath
import com.querydsl.jpa.impl.JPAQuery


abstract class AbstractQueryDslService {
    
    @Autowired
    private EntityManager entityManager;
    
    protected JPAQuery from(EntityPath<?>... paths) {
        return new JPAQuery(entityManager).from(paths);
    }
    
    public void setEntityMananger(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
