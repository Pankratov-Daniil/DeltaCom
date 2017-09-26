package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Option;

/**
 * SqlRepository for Option entity
 */
public class OptionSqlRepository extends SqlRepository {
    public OptionSqlRepository() {
        super(Option.class);
    }
}
