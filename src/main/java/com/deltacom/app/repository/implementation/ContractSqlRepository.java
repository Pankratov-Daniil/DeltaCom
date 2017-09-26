package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Contract;

/**
 * SqlRepository for Client entity
 */
public class ContractSqlRepository extends SqlRepository {
    public ContractSqlRepository() {
        super(Contract.class);
    }
}
