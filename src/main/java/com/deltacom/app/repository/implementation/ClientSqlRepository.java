package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;

/**
 * SqlRepository for Client entity
 */
public class ClientSqlRepository extends SqlRepository {
    public ClientSqlRepository() {
        super(Client.class);
    }
}
