package com.proggettazione.richiesteConsapBE.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String utenteCreazione;
    private LocalDateTime dataInserimento;
    private LocalDateTime dataModifica;
    private String utenteModifica ;
}
