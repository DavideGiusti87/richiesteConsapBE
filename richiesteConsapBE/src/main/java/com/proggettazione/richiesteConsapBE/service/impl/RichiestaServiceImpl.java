package com.proggettazione.richiesteConsapBE.service.impl;

import com.proggettazione.richiesteConsapBE.model.Richiesta;
import com.proggettazione.richiesteConsapBE.model.StatoApprovazione;
import com.proggettazione.richiesteConsapBE.repository.RichiestaRepository;
import com.proggettazione.richiesteConsapBE.repository.StatoRepository;
import com.proggettazione.richiesteConsapBE.service.RichiestaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class RichiestaServiceImpl implements RichiestaService {

    @Autowired
    RichiestaRepository richiestaRepository;

    @Autowired
    StatoRepository statoRepository;

    public Richiesta crea(Principal principal, Richiesta nuovaRichiesta){
        Richiesta richiesta = inserisciDati(new Richiesta(),nuovaRichiesta);
        richiesta.setUtenteCreazione(principal.getName());
        richiesta.setDataInserimento(LocalDateTime.now());
        return richiestaRepository.save(richiesta);
    }

    @Override
    public List<Richiesta> getAll(){
        return richiestaRepository.findAll();
    }

    @Override
    public Richiesta getById(int id) {
        return richiestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("La richiesta con id %d non è stata trovata", id)
                ));

    }

    @Override
    public Richiesta modifica(Principal principal, int id, Richiesta richiestaModificata) {
        Richiesta richiesta = inserisciDati(getById(id),richiestaModificata);
        richiesta.setDataModifica(LocalDateTime.now());
        richiesta.setUtenteModifica(principal.getName());
        return richiestaRepository.save(richiesta);
    }


//--------------------------------------Metodi funzionali della classe--------------------------------------
    private Richiesta inserisciDati(Richiesta richiesta, Richiesta nuovaRichiesta){
        richiesta.setIdCommessa(nuovaRichiesta.getIdCommessa());
        richiesta.setOggetto(nuovaRichiesta.getOggetto());
        richiesta.setIdStato(
                statoRepository.findById(
                        nuovaRichiesta.getIdStato().getId()
                ).orElse(null)
        );

        if(nuovaRichiesta.getStatoApprovazione()!=null){
            richiesta.setStatoApprovazione(
                    StatoApprovazione.valueOf(
                            (nuovaRichiesta.getStatoApprovazione()).name()
                    )
            );
        }else
            richiesta.setStatoApprovazione(null);

        //TODO controllo format data
        richiesta.setDataCreazione(nuovaRichiesta.getDataCreazione());
        richiesta.setNote(nuovaRichiesta.getNote());
        richiesta.setCampo1(nuovaRichiesta.getCampo1());
        richiesta.setCampo2(nuovaRichiesta.getCampo2());
        richiesta.setCampo3(nuovaRichiesta.getCampo3());
        richiesta.setCampo4(nuovaRichiesta.getCampo4());
        return richiesta;
    }

    /*private LocalDateTime formattaData(LocalDateTime data){

    }*/
}
