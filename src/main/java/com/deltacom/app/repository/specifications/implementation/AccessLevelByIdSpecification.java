package com.deltacom.app.repository.specifications.implementation;

import com.deltacom.app.repository.specifications.api.SqlSpecification;

/**
 * Allows find AccessLevel entity in database by id
 */
public class AccessLevelByIdSpecification implements SqlSpecification {

    private final int id;

    public AccessLevelByIdSpecification(int id){
        this.id = id;
    }

    @Override
    public String toSqlQuery(){
        return "from AccessLevel acLv where id = " + id;
    }
}
