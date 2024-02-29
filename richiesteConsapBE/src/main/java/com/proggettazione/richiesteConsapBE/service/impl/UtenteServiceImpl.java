package com.proggettazione.richiesteConsapBE.service.impl;

import com.proggettazione.richiesteConsapBE.model.Utente;
import com.proggettazione.richiesteConsapBE.repository.UtenteRepository;
import com.proggettazione.richiesteConsapBE.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class UtenteServiceImpl implements UtenteService, UserDetailsService {

    @Autowired
    UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //cerchiamo se l'utente è già presente nel DB
        Utente utente = utenteRepository.findByUsername(username);
        if(utente != null){
            return new User(utente.getUsername(), utente.getPassword(), Collections.emptyList());
        }else {
            String messaggio = String.format("L'utente con l'username %s non è stato trovato", username);
            throw new UsernameNotFoundException(messaggio);
        }
    }
}
