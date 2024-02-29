package com.proggettazione.richiesteConsapBE.service;

import com.proggettazione.richiesteConsapBE.model.Richiesta;

import java.security.Principal;
import java.util.List;

public interface RichiestaService {
    Richiesta crea(Principal principal, Richiesta nuovaRichiesta);
    List<Richiesta> getAll();
    Richiesta getById(int id);
    Richiesta modifica(Principal principal,int id,Richiesta richiestaModificata);

}
