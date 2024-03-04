package com.proggettazione.richiesteConsapBE.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
