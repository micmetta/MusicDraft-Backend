package com.microservice.home.repository;
import com.microservice.home.model.GestioneAmici;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeRepository extends CrudRepository<GestioneAmici, Long> {

    //List<GestioneAmici> findByNickameU1AndNickameU2(String nickameU1, String nickameU2);
}
