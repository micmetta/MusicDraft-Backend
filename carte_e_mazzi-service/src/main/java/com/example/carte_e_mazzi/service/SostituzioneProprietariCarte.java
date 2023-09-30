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


    public void aggiorna_proprietario(String id_carta, String nickname_nuovo_proprietario){

        List<CarteBraniN> carta_da_aggiornare = repoB.findById(id_carta);

        if(!carta_da_aggiornare.isEmpty()){
            carta_da_aggiornare.get(0).setNickname(nickname_nuovo_proprietario);
            repoB.save(carta_da_aggiornare.get(0)); // memorizzo l'aggiornamento nel DB
            System.out.println();
            System.out.println("Aggiornamento proprietario carta eseguito con successo.");
        }
        else{

            // controllo se la carta è di tipo artista:
            List<CarteArtistiN> carta_da_aggiornare_2 = repoA.findById(id_carta);
            if(!carta_da_aggiornare_2.isEmpty()){
                carta_da_aggiornare_2.get(0).setNickname(nickname_nuovo_proprietario);
                repoA.save(carta_da_aggiornare_2.get(0)); // memorizzo l'aggiornamento nel DB
                System.out.println();
                System.out.println("Aggiornamento proprietario carta eseguito con successo.");
            }
            else{
                System.out.println();
                System.out.println("ERRORE in -aggiorna_proprietario-: Non è stata trovata nessuna carta con l'id inserito.");
            }
        }
    }

}
