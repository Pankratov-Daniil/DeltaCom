package com.deltacom.app.repository.api;

import com.deltacom.app.entities.Client;

public interface ClientRepository {
    public Client getClientByEmail(String email);
}
