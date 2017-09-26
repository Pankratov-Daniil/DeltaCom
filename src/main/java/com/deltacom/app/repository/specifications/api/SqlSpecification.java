package com.deltacom.app.repository.specifications.api;

import javax.persistence.Query;

public interface HqlSpecification extends Specification {
    Query toHqlQuery();
}
