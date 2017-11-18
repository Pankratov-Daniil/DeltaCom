package com.deltacom.app.security;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Client;
import com.deltacom.app.repository.api.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for getting client and his access levels from database for Spring Security.
 */
@Service("ClientLoginSecurity")
public class ClientLoginSecurity implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Gets client and his access levels from database by his email
     * @param email clients email
     * @return UserDetails contains client information
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.getClientByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException(email + " not found.");
        } else if(client.getPassword().length() < 6 || !client.isActivated()) {
            throw new UsernameNotFoundException(email + " is not activated.");
        }
        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        if(client.getTwoFactorAuth()) {
            auths.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH"));
        } else {
            for (AccessLevel accessLevel : client.getAccessLevels()) {
                auths.add(new SimpleGrantedAuthority(accessLevel.getName()));
            }
        }
        return new User(email, client.getPassword(), true, true, true, true, auths);
    }
}
