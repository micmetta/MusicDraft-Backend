package com.microservice.matchmaking.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.matchmaking.model.RiepilogoPartitaConclusa;
import com.microservice.matchmaking.repository.RiepilogoPartitaConclusaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matchmakingService/riepilogoPartitaConclusaController")
public class RiepilogoPartitaConclusaController {

    @Autowired
    RiepilogoPartitaConclusaRepository riepilogo_partita_conclusa_repository;


    // questo endpoint permette di aggiungere una nuova partita conclusa nel DB
    @PostMapping(value = "/addPartitaConclusa")
    public String add_partita_conclusa(@RequestBody RiepilogoPartitaConclusa riepilogoPartitaConclusa) throws JsonProcessingException {

        //Adesso faccio il parsing della stringa JSON carteAssociateMazzoU1 e carteAssociateMazzoU2 e la converto in una lista di stringhe:
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapper_2 = new ObjectMapper();
        ObjectMapper objectMapper_3 = new ObjectMapper();
        ObjectMapper objectMapper_4 = new ObjectMapper();

        List<String> carteAssociateMazzoU1 = objectMapper.readValue(riepilogoPartitaConclusa.getCarteAssociateMazzoU1(), new TypeReference<List<String>>() {});
        List<String> carteAssociateMazzoU2 = objectMapper_2.readValue(riepilogoPartitaConclusa.getCarteAssociateMazzoU2(), new TypeReference<List<String>>() {});


        // la data passata in input al backend deve essere nel formato: "yyyy-MM-dd" es. "2023-09-25"
        LocalDate dataPartita = riepilogoPartitaConclusa.getDataGiocata(); // prendo la data fornita nell'oggeto di input,
        // nella quale si è conclusa la partita corrente

        riepilogo_partita_conclusa_repository.save(new RiepilogoPartitaConclusa(dataPartita, riepilogoPartitaConclusa.getNickVincitore(),
                riepilogoPartitaConclusa.getNicknameU1(), riepilogoPartitaConclusa.getNicknameU2(),
                riepilogoPartitaConclusa.getNomemazzoU1(), riepilogoPartitaConclusa.getNomemazzoU2(),
                objectMapper.writeValueAsString(carteAssociateMazzoU1), objectMapper_2.writeValueAsString(carteAssociateMazzoU2),
                riepilogoPartitaConclusa.getPopolaritaMazzoU1(), riepilogoPartitaConclusa.getPopolaritaMazzoU2()));

        return "Riepilogo partita aggiunta con successo.";
    }


    // questo endpoint restituisce tutte le partite concluse da un certo giocatore tramite il suo nickname
    @GetMapping(value = "/getAllPartiteConcluse/{nickname}")
    public List<RiepilogoPartitaConclusa> get_allPartiteConcluse(@PathVariable String nickname){

        List<RiepilogoPartitaConclusa> lista_partite_concluse = riepilogo_partita_conclusa_repository.findByNicknameU1(nickname);
        List<RiepilogoPartitaConclusa> lista_partite_concluse_come_U2 = riepilogo_partita_conclusa_repository.findByNicknameU2(nickname);

        // Unisco lista_partite_concluse_come_U2 in lista_partite_concluse_come_U1
        lista_partite_concluse.addAll(lista_partite_concluse_come_U2);

        return lista_partite_concluse;
    }

}
