package com.proggettazione.richiesteConsapBE.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.proggettazione.richiesteConsapBE.model.Utente;

import java.text.ParseException;
import java.util.Map;

public interface UtenteService {
    Utente crea(Utente utente) throws Exception;

    Map<String, String> refreshToken(String authorizationHeader, String string) throws BadJOSEException, ParseException, JOSEException;
}
