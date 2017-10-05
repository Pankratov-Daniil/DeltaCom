package com.deltacom.app.repository.api;

import com.deltacom.app.entities.Client;

/**
 * Repository for Client
 */
public interface ClientRepository {
    public Client getClientByEmail(String email);
}
