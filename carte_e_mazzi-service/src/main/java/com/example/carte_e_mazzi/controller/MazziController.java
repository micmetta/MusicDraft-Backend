package com.example.carte_e_mazzi.controller;

import com.example.carte_e_mazzi.model.CarteArtistiN;
import com.example.carte_e_mazzi.model.CarteBraniN;
import com.example.carte_e_mazzi.model.CarteInVenditaArtista;
import com.example.carte_e_mazzi.model.Mazzi;
import com.example.carte_e_mazzi.repo.MazziRepo;
import com.example.carte_e_mazzi.repo.UtenteRepoCarteArtisti;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartemazzi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MazziController {

    public static class MazzoDTO {
        private String nomeMazzo;
        private List<?> carteSelezionate;

        private  String nickname;

        // Getter e setter per nome e carteSelezionate


        public String getNomeMazzo() {
            return nomeMazzo;
        }

        public void setNomeMazzo(String nomeMazzo) {
            this.nomeMazzo = nomeMazzo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public List<?> getCarteSelezionate() {
            return carteSelezionate;
        }

        public void setCarteSelezionate(List<?> carteSelezionate) {
            this.carteSelezionate = carteSelezionate;
        }



        public MazzoDTO() {
            // Costruttore vuoto necessario per la deserializzazione JSON
        }

        public MazzoDTO(String nome, List<?> carteSelezionate, String nick) {
            this.nomeMazzo = nome;
            this.carteSelezionate = carteSelezionate;
            this.nickname = nick;
        }
    }

    @Autowired
    MazziRepo repo;

    @GetMapping("/showMazzi/{nickname}")
    public List<Mazzi> show_mazzi(@PathVariable String nickname){
        List<Mazzi> mazzi = repo.findByNickname(nickname);
        mazzi.toString();
        return mazzi;
    }

    @GetMapping("/esisteCardinMazzo/{nickname}/{id_carta}")
    public Boolean get_carta(@PathVariable String id_carta,@PathVariable String nickname){
        List<Mazzi> mazzi = repo.findByCartaassociataAndNickname(id_carta,nickname);
        if(mazzi.size()==0){
            return false;
        }else {
            return true;
        }
    }


    @DeleteMapping("/deleteMazzo/{nomeMazzo}")
    public void delete_mazzo(@PathVariable String nomeMazzo){

        for(Mazzi i : repo.findByNomemazzo(nomeMazzo)) {
            repo.delete(i);
        }
    }
    @DeleteMapping("/deleteMazzobyNick/{nickname}")
    public void delete_mazzo_by_nick(@PathVariable String nickname){

        for(Mazzi i : repo.findByNickname(nickname)) {
            repo.delete(i);
        }
    }

    @PostMapping("/salvaMazzo")
    public void salva_mazzo(@RequestBody MazzoDTO mazzoDTO) {

        String nomeMazzo = mazzoDTO.getNomeMazzo(); // Ottieni il nome del mazzo dal DTO
        String nickname =mazzoDTO.getNickname();
        List<?> carteSelezionate = mazzoDTO.getCarteSelezionate();// Ottieni l'elenco delle carte selezionate dal DTO
        System.out.println(nomeMazzo+" "+nickname+" "+carteSelezionate+"  "+repo.findByNomemazzo(nomeMazzo).size());
        if((repo.findByNomemazzo(nomeMazzo).size()==0)) {
            System.out.println("Ciò");
            float pop = 0;
            for (Object cartaDTO : carteSelezionate) {
                String s = cartaDTO.toString();
                if (s.contains("genere")) {
                    CarteArtistiN artista = new ObjectMapper().convertValue(cartaDTO, CarteArtistiN.class);
                    pop+=artista.getPopolarita();
                }else if (s.contains("durata")){
                    CarteBraniN brano = new ObjectMapper().convertValue(cartaDTO, CarteBraniN.class);
                    pop+=brano.getPopolarita();
                }
            }
            pop = (pop/carteSelezionate.size());
            for (Object cartaDTO : carteSelezionate) {
                String s = cartaDTO.toString();
                if (s.contains("genere")) {
                    CarteArtistiN artista = new ObjectMapper().convertValue(cartaDTO, CarteArtistiN.class);
                    repo.save(new Mazzi(nomeMazzo, artista.getId(), nickname,pop));
                } else if (s.contains("durata")) {
                    System.out.println("Brano");
                    CarteBraniN brano = new ObjectMapper().convertValue(cartaDTO, CarteBraniN.class);
                    repo.save(new Mazzi(nomeMazzo, brano.getId(), nickname,pop));
                }
            }
        }
    }



}
