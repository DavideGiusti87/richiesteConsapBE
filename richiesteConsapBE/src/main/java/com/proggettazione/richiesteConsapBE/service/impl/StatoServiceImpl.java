package com.proggettazione.richiesteConsapBE.service.impl;

import com.proggettazione.richiesteConsapBE.model.Stato;
import com.proggettazione.richiesteConsapBE.repository.StatoRepository;
import com.proggettazione.richiesteConsapBE.service.StatoService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;

@Service
public class StatoServiceImpl implements StatoService {

    @Autowired
    private StatoRepository statoRepository;
    @Override
    public Stato crea(Stato stato) {
        Stato statoDB = statoRepository.findByNomeStato(stato.getNomeStato()).orElse(null);
        if (statoDB == null){
            statoDB = new Stato();
            statoDB.setNomeStato(stato.getNomeStato());
            statoDB.setUtenteCreazione("principal.getName");
            statoDB.setDataInserimento(LocalDateTime.now());
            return statoRepository.save(statoDB);
        }else{
            throw new EntityExistsException(String.format("Stato con il nome %s già presente nel database", stato.getNomeStato()));
        }

    }

    public void implementaStati(){
        String[] stati = {"Open", "Assigned", "OTE CONSEGNATO", "APPR. CODIFICA E TEST", "APPR. ANALISI", "Sviluppo"};
        for (String stato : stati) {
            if (statoRepository.existsByNomeStato(stato)){
                System.out.println("Stato già presente nel database: " + stato);
            }else{
                Stato nuovoStato = new Stato();
                nuovoStato.setNomeStato(stato);
                crea(nuovoStato);
                System.out.println("Stato creato con successo: " + stato);
            }
        }

    }
}
