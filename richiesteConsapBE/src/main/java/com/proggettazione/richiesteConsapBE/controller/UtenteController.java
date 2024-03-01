package com.proggettazione.richiesteConsapBE.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proggettazione.richiesteConsapBE.model.Utente;
import com.proggettazione.richiesteConsapBE.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @PostMapping("/")
    public ResponseEntity<?> crea(@RequestBody Utente utente){
        try {
            return ResponseEntity.ok(utenteService.crea(utente));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la creazione dell'utente: "+e.getMessage());
        }
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Estrae l'intestazione "Authorization" dalla richiesta HTTP per ottenere il token di aggiornamento
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        // Verifica se l'intestazione non è nulla e se inizia con "Bearer ", indicando che contiene un token di accesso
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // Chiama utenzaService.refreshToken per aggiornare il token
                Map<String, String> tokenMap = utenteService.refreshToken(authorizationHeader, request.getRequestURL().toString());

                // Se il token viene aggiornato con successo, aggiunge i nuovi token all'intestazione della risposta HTTP
                response.addHeader("access_token", tokenMap.get("access_token"));
                response.addHeader("refresh_token", tokenMap.get("refresh_token"));
            } catch (Exception e) {
                // Se si verifica un errore durante l'aggiornamento del token, gestisce l'eccezione e restituisce un messaggio di errore JSON
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("errorMessage", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            // Se non è presente un token di aggiornamento nell'intestazione della richiesta, restituisce un errore
            throw new RuntimeException("Token di aggiornamento mancante");
        }
    }
}
