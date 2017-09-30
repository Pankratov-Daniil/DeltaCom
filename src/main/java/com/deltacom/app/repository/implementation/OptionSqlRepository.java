package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Option;
import org.springframework.stereotype.Repository;

/**
 * SqlRepository for Option entity.
 */
@Repository("Option")
public class OptionSqlRepository extends SqlRepository<Option> {
    public OptionSqlRepository() {
        super(Option.class);
    }
}
