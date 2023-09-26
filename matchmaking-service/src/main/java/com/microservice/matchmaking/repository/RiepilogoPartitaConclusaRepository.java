package com.microservice.matchmaking.repository;

import com.microservice.matchmaking.model.RiepilogoPartitaConclusa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiepilogoPartitaConclusaRepository extends JpaRepository<RiepilogoPartitaConclusa, Long> {

    List<RiepilogoPartitaConclusa> findByNicknameU1(String nicknameU1);
    List<RiepilogoPartitaConclusa> findByNicknameU2(String nicknameU2);


}
