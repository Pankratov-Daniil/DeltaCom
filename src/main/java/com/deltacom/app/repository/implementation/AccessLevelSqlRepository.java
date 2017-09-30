package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.AccessLevel;
import org.springframework.stereotype.Repository;

/**
 * SqlRepository for AccessLevel entity
 */
@Repository("AccessLevel")
public class AccessLevelSqlRepository extends SqlRepository<AccessLevel> {
    public AccessLevelSqlRepository(){
        super(AccessLevel.class);
    }
}
