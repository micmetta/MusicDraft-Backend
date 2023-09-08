package com.example.carte_e_mazzi.service;

import com.example.carte_e_mazzi.model.CarteArtistiN;
import com.example.carte_e_mazzi.model.CarteBraniN;
import com.example.carte_e_mazzi.repo.UtenteRepoCarteArtisti;
import com.example.carte_e_mazzi.repo.UtenteRepoCarteBrano;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SostituzioneProprietariCarte {

    private final UtenteRepoCarteArtisti repoA;
    private final UtenteRepoCarteBrano repoB;


    // metodo che prende in input id_carta e nickname e si preoccupa di sostituire il nickname_corrente della carta (di tipo ARTISTA)
    // con il nickname passato in input.
    public void aggiorna_proprietario_cartaArtista(String id_carta, String nickname_nuovo_proprietario){

        List<CarteArtistiN> carta_da_aggiornare = repoA.findById(id_carta);

        if(!carta_da_aggiornare.isEmpty()){
            carta_da_aggiornare.get(0).setNickname(nickname_nuovo_proprietario);
            repoA.save(carta_da_aggiornare.get(0)); // memorizzo l'aggiornamento nel DB
            System.out.println();
            System.out.println("Aggiornamento proprietario carta eseguito con successo.");
        }
        else{
            System.out.println();
            System.out.println("ERRORE in -aggiorna_proprietario-: Non è stata trovata nessuna carta con l'id inserito.");
        }
    }


    // metodo che prende in input id_carta e nickname e si preoccupa di sostituire il nickname_corrente della carta (di tipo BRANO)
    // con il nickname passato in input.
    public void aggiorna_proprietario_cartaBrano(String id_carta, String nickname_nuovo_proprietario){

        List<CarteBraniN> carta_da_aggiornare = repoB.findById(id_carta);

        if(!carta_da_aggiornare.isEmpty()){
            carta_da_aggiornare.get(0).setNickname(nickname_nuovo_proprietario);
            repoB.save(carta_da_aggiornare.get(0)); // memorizzo l'aggiornamento nel DB
            System.out.println();
            System.out.println("Aggiornamento proprietario carta eseguito con successo.");
        }
        else{
            System.out.println();
            System.out.println("ERRORE in -aggiorna_proprietario-: Non è stata trovata nessuna carta con l'id inserito.");
        }
    }


}
