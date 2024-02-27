package com.proggettazione.richiesteConsapBE.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
public class Richiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long idCommessa;
    private String Oggetto;
    private Date dataCreazione;
    private String note;
    private String campo1;
    private String campo2;
    private String campo3;
    private String campo4;
    private long idUtenteCreazione;
    private Date dataInserimento;
    private Date dataModifica;
    private long idUtenteModifica;
}
