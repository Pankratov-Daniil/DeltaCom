package com.deltacom.app.repository.specifications.api;

/**
 * SqlSpecification for Generic Repository.
 * Implementing it allows make queries in repository.
 */
public interface SqlSpecification extends Specification {
    String toSqlQuery();
}
