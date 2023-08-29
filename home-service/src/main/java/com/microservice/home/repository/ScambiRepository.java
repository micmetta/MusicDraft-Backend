package com.microservice.home.repository;

import com.microservice.home.model.GestioneScambi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScambiRepository extends CrudRepository<GestioneScambi, Long> {
    List<GestioneScambi> findByNicknameU2(String nicknameU1);
    GestioneScambi findById(long id);
    List<GestioneScambi> findByIdStartAndStatoOfferta(long idStart, String statoOfferta);
    List<GestioneScambi> findByIdStart(long idStart);
}
