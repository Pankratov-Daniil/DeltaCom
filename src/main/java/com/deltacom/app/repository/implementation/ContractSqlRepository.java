package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Contract;
import org.springframework.stereotype.Repository;

/**
 * SqlRepository for Client entity
 */
@Repository("Contract")
public class ContractSqlRepository extends SqlRepository<Contract> {
    public ContractSqlRepository() {
        super(Contract.class);
    }
}
