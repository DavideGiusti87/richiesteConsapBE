package com.proggettazione.richiesteConsapBE.service.impl;

import com.proggettazione.richiesteConsapBE.model.Richiesta;
import com.proggettazione.richiesteConsapBE.repository.RichiestaRepository;
import com.proggettazione.richiesteConsapBE.service.RichiestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RichiestaServiceImpl implements RichiestaService {

    @Autowired
    RichiestaRepository richiestaRepository;

    public String crea(Richiesta richiesta){
        //Amministratore
        return null;
    }

}
