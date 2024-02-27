package com.proggettazione.richiesteConsapBE.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Stato {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nomeStato;
    private String utenteCreazione;
    private LocalDateTime dataInserimento;
    private LocalDateTime dataModifica;
    private String utenteModifica;
}
