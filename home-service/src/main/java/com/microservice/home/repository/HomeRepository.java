package com.microservice.home.repository;
import com.microservice.home.model.GestioneAmici;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeRepository extends CrudRepository<GestioneAmici, Long> {

    GestioneAmici findByNicknameU1AndNicknameU2(String nicknameU1, String nicknameU2);
    List<GestioneAmici> findByNicknameU2AndStato(String nicknameU2, String stato);
}
