package com.proggettazione.richiesteConsapBE.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Richiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int idCommessa;
    private String Oggetto;
    @ManyToOne
    @JoinColumn(name = "id_Stato", referencedColumnName = "id")
    private Stato idStato;
    private int statoApprovazione;
    private LocalDateTime dataCreazione;
    private String note;
    private String campo1;
    private String campo2;
    private String campo3;
    private String campo4;
    private String utenteCreazione;
    private LocalDateTime dataInserimento;
    private LocalDateTime dataModifica;
    private String utenteModifica;

}
