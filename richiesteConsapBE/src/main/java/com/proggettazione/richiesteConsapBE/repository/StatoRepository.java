package com.proggettazione.richiesteConsapBE.repository;

import com.proggettazione.richiesteConsapBE.model.Stato;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatoRepository extends JpaRepository<Stato,Integer> {

    Optional<Stato> findByNomeStato(String nomeStato);

    boolean existsByNomeStato(String nomeStato);
}
