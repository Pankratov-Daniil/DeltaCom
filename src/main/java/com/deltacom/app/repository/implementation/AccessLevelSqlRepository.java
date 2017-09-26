package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.AccessLevel;

/**
 * SqlRepository for AccessLevel entity
 */
public class AccessLevelSqlRepository extends SqlRepository {
    public AccessLevelSqlRepository(){
        super(AccessLevel.class);
    }
}
