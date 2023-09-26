package com.microservice.matchmaking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.matchmaking.model.RiepilogoPartitaConclusa;
import com.microservice.matchmaking.repository.RiepilogoPartitaConclusaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RiepPartita {


    private final RiepilogoPartitaConclusaRepository riepilogo_partita_conclusa_repository;

    public void addPartitaConclusa(RiepilogoPartitaConclusa riepilogoPartitaConclusa) throws JsonProcessingException {
        riepilogo_partita_conclusa_repository.save(riepilogoPartitaConclusa);
    }

}
