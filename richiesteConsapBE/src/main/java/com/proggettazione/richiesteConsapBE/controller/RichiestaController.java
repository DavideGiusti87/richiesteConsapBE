package com.proggettazione.richiesteConsapBE.controller;

import com.proggettazione.richiesteConsapBE.model.Richiesta;
import com.proggettazione.richiesteConsapBE.service.RichiestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/richiesta")
public class RichiestaController {
    @Autowired
    RichiestaService richiestaService;

    @PutMapping
    public ResponseEntity<?> creaRichiesta(Principal principal, @RequestBody Richiesta nuovaRichiesta){
        try {
            Richiesta richiesta = richiestaService.crea(principal, nuovaRichiesta);
            return ResponseEntity.ok(richiesta);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la registrazione della richiesta:\n"+e.getMessage());
        }
    }
}
