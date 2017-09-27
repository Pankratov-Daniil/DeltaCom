package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;
import org.springframework.stereotype.Repository;

/**
 * SqlRepository for Client entity
 */
@Repository("Client")
public class ClientSqlRepository extends SqlRepository {
    public ClientSqlRepository() {
        super(Client.class);
    }
}
