package com.example.carte_e_mazzi.controller;

import com.example.carte_e_mazzi.model.CarteArtistiN;
import com.example.carte_e_mazzi.model.CarteBraniN;
import com.example.carte_e_mazzi.model.CarteInVenditaArtista;
import com.example.carte_e_mazzi.model.CarteInVenditaTrack;
import com.example.carte_e_mazzi.repo.UtenteRepoCarteArtisti;
import com.example.carte_e_mazzi.repo.UtenteRepoCarteBrano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cartemazzi")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class CarteController {

    @Autowired
    UtenteRepoCarteArtisti repoA;

    @Autowired
    UtenteRepoCarteBrano repoT;

    @PostMapping("/receive-data")
    public String receiveDataFromFrontend(@RequestBody String data) {

        System.out.println("Dati ricevuti dal frontend: " + data);

        return data;
    }

    @PostMapping("/showCardArtistaUtente")
    public List<CarteArtistiN> crea_Card_artista(@RequestBody String received) {

        List<CarteArtistiN> carteA = new ArrayList<>();
        repoA.findByNickname(received).forEach(carteA::add);

         // Assumendo che ci sia un repositoryT per le CarteInVenditaTrack

        return carteA;
    }
    @PostMapping("/showCardArtistaBrani")
    public List<CarteBraniN> crea_Card_brano(@RequestBody String received) {
        List<CarteBraniN> carteT = new ArrayList<>();
        repoT.findByNickname(received).forEach(carteT::add);
        return carteT;
    }
        @PostMapping("/acquistaCartaArtista")
        public void insert_cart_Artist(@RequestBody CarteArtistiN data){
            System.out.println(data.getNome());
            CarteArtistiN _artista = repoA.save(new CarteArtistiN(data.getNome(), data.getPopolarita(), data.getGenere(), data.getImmagine(),data.getNickname()));
        }
        @PostMapping("/acquistaCartaBrano")
        public void insert_cart_Artist(@RequestBody CarteBraniN data){
            System.out.println(data.getNome());
            CarteBraniN _track = repoT.save(new CarteBraniN(data.getNome(),data.getDurata(),data.getAnno_pubblicazione(), data.getPopolarita(), data.getImmagine(),data.getNickname()));
    }


    }



