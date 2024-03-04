package com.proggettazione.richiesteConsapBE.model.DTO;

import com.proggettazione.richiesteConsapBE.model.Stato;
import com.proggettazione.richiesteConsapBE.model.StatoApprovazione;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RichiestaDTO {
    private int idCommessa;
    private String oggetto;
    private int idStato;
    private String dataCreazione;
    private String statoApprovazione;
    private String note;
    private String campo1;
    private String campo2;
    private String campo3;
    private String campo4;
}
