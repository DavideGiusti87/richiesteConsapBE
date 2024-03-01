package com.proggettazione.richiesteConsapBE.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.proggettazione.richiesteConsapBE.configuration.JwtUtil;
import com.proggettazione.richiesteConsapBE.model.Utente;
import com.proggettazione.richiesteConsapBE.repository.UtenteRepository;
import com.proggettazione.richiesteConsapBE.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service
public class UtenteServiceImpl implements UtenteService, UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //cerchiamo se l'utente è già presente nel DB
        Utente utente = utenteRepository.findByUsername(username);
        if (utente != null) {
            System.out.println("Trovata username nel database");
            /*Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            utente.getRuoli().forEach(ruolo -> {
                authorities.add(new SimpleGrantedAuthority(ruolo.getNome()));
            });*/
            return new User(utente.getUsername(), utente.getPassword(), Collections.emptyList()/*authorities*/);
        } else {
            String messaggio = String.format("L'username %s non è stato trovato", username);
            System.out.println(messaggio);
            throw new UsernameNotFoundException(messaggio);
        }
    }

    @Override
    public Utente crea(Utente utente) throws Exception {
        Utente utenteDB = utenteRepository.findByUsername(utente.getUsername());
        if(utenteDB == null){
            utenteDB = new Utente();
            utenteDB.setUsername(utente.getUsername());
            utenteDB.setPassword(passwordEncoder.encode(utente.getPassword()));
            utenteDB.setDataInserimento(LocalDateTime.now());
            return utenteRepository.save(utenteDB);
        }else {
            throw new Exception(String.format("Utente con username %s già presente",utente.getUsername()));
        }
    }

    public Map<String, String> refreshToken(String autorizzazioneHeader, String emettitore) throws BadJOSEException, ParseException, JOSEException {

        String refreshToken = autorizzazioneHeader.substring("Bearer ".length());
        UsernamePasswordAuthenticationToken authenticationToken = JwtUtil.parseToken(refreshToken);
        String username = authenticationToken.getName();
        Utente utenza = utenteRepository.findByUsername(username);
        String accessToken = JwtUtil.createAccessToken(username, emettitore,new ArrayList<>());
        return Map.of("access_token", accessToken, "refresh_token", refreshToken);
    }

}
