package com.proggettazione.richiesteConsapBE.service.impl;

import com.proggettazione.richiesteConsapBE.model.DTO.RichiestaDTO;
import com.proggettazione.richiesteConsapBE.model.Richiesta;
import com.proggettazione.richiesteConsapBE.model.StatoApprovazione;
import com.proggettazione.richiesteConsapBE.repository.RichiestaRepository;
import com.proggettazione.richiesteConsapBE.repository.StatoRepository;
import com.proggettazione.richiesteConsapBE.service.RichiestaService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@Service
public class RichiestaServiceImpl implements RichiestaService {

    @Autowired
    private RichiestaRepository richiestaRepository;
    @Autowired
    private StatoRepository statoRepository;

    public List<Richiesta> getAll(){
        return richiestaRepository.findAll();
    }

    @Override
    public Richiesta getById(int id) {
        Richiesta richiesta = richiestaRepository.findById(id).orElse(null);
        if (richiesta != null){
            return richiesta ;
        }else {
            String messaggio = String.format("Richiesta con id %d non trovata", id);
            throw new EntityNotFoundException(messaggio);
        }

    }

    @Override
    public Richiesta crea(RichiestaDTO richiesta) {
        Richiesta richiestaDB = richiestaRepository.findByIdCommessa(richiesta.getIdCommessa()).orElse(null);
        if (richiestaDB == null) {
            richiestaDB = new Richiesta();
            richiestaDB.setIdCommessa(richiesta.getIdCommessa());
            richiestaDB.setOggetto(richiesta.getOggetto());
            richiestaDB.setIdStato(statoRepository.findById(richiesta.getIdStato()).orElse(null));
            richiestaDB.setDataCreazione(stringDataOraToLocalDateTime(richiesta.getDataCreazione()));
            //richiestaDB.setStatoApprovazione(StatoApprovazione.values()[richiesta.getStatoApprovazione()]);
            if (richiesta.getStatoApprovazione() != null) {
                richiestaDB.setStatoApprovazione(StatoApprovazione.valueOf(richiesta.getStatoApprovazione()));
            } else {
                richiestaDB.setStatoApprovazione(null);
            }
            richiestaDB.setNote(richiesta.getNote());
            richiestaDB.setCampo1(richiesta.getCampo1());
            richiestaDB.setCampo2(richiesta.getCampo2());
            richiestaDB.setCampo3(richiesta.getCampo3());
            richiestaDB.setCampo4(richiesta.getCampo4());
            richiestaDB.setUtenteCreazione("principal.getName");
            richiestaDB.setDataInserimento(LocalDateTime.now());
            return richiestaRepository.save(richiestaDB);
        }        else throw new EntityExistsException(String.format("Richiesta con id %d già presente nel database", richiesta.getIdCommessa()));

    }

    @SneakyThrows
    public LocalDateTime stringDataOraToLocalDateTime(String dataDaFormattare) {
        try {
            // Definire il formato di input
            DateTimeFormatter formatoInput = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Effettuare il parsing della data nel formato di input
            LocalDateTime dataFormattata = LocalDateTime.parse(dataDaFormattare, formatoInput);

            return dataFormattata;
        } catch (DateTimeParseException e) {
            // Gestire l'eccezione nel caso in cui il formato della data fornita non sia valido
            e.printStackTrace();
            String messaggio = String.format("il formato della data fornita non valido: %s",dataDaFormattare);
            throw new IllegalArgumentException(messaggio+e.getMessage());
        }
    }

}



