package com.proggettazione.richiesteConsapBE.service;

import com.proggettazione.richiesteConsapBE.model.DTO.RichiestaDTO;
import com.proggettazione.richiesteConsapBE.model.Richiesta;

import java.util.List;

public interface RichiestaService {

    List<Richiesta> getAll();
    Richiesta getById(int id);

    Richiesta crea(RichiestaDTO richiesta);
}
