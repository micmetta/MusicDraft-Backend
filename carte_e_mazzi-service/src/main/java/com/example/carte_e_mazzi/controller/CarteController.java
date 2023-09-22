package com.example.carte_e_mazzi.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.carte_e_mazzi.model.CarteArtistiN;
import com.example.carte_e_mazzi.model.CarteBraniN;
import com.example.carte_e_mazzi.model.CarteInVenditaArtista;
import com.example.carte_e_mazzi.model.CarteInVenditaTrack;
import com.example.carte_e_mazzi.repo.UtenteRepoCarteArtisti;
import com.example.carte_e_mazzi.repo.UtenteRepoCarteBrano;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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



    @GetMapping("/showCardArtistaUtente/{received}")
    public List<CarteArtistiN> crea_Card_artista(@PathVariable String received) {

        List<CarteArtistiN> carteA = new ArrayList<>();
        repoA.findByNickname(received).forEach(carteA::add);

         // Assumendo che ci sia un repositoryT per le CarteInVenditaTrack

        return carteA;
    }
    @GetMapping("/showCardArtistaBrani/{received}")
    public List<CarteBraniN> crea_Card_brano(@PathVariable String received) {
        List<CarteBraniN> carteT = new ArrayList<>();
        repoT.findByNickname(received).forEach(carteT::add);
        return carteT;
    }
        @PostMapping("/acquistaCartaArtista/{nickname}")
        public ResponseEntity<?> insert_cart_Artist(@PathVariable String nickname,@RequestBody Object data1){
            System.out.println(data1);
            CarteInVenditaArtista data = new ObjectMapper().convertValue(data1, CarteInVenditaArtista.class);
            String nicknameAcquirente = nickname;
            String nicknameAcquistante = data.getNick();
            System.out.println(nicknameAcquirente+","+nicknameAcquistante);
            System.out.println(nickname);
            RestTemplate restTemplate1 = new RestTemplate();
            ResponseEntity<Integer> pointUtente = restTemplate1.getForEntity("http://authentication-service:8081/api/v1/authenticationService/getPoints/" + nicknameAcquirente, Integer.class);
            System.out.println(pointUtente.getBody());
            if(pointUtente.getBody() > data.getCosto() && !(nicknameAcquirente.equals(nicknameAcquistante))) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange("http://authentication-service:8081/api/v1/authenticationService/updatePoints/" + nicknameAcquirente + "/" + (-data.getCosto()), HttpMethod.PUT,requestEntity, String.class);
                System.out.println(responseEntity.getBody());
                if(!(data.getNick().equals("admin"))){
                    RestTemplate restTemplate2 = new RestTemplate();
                    ResponseEntity<String> response = restTemplate2.getForEntity("http://authentication-service:8081/api/v1/authenticationService/findByNickname/"+nicknameAcquistante, String.class);
                    if(response.getBody().equals("nickname esistente")){
                        RestTemplate restTemplate4 = new RestTemplate();
                        HttpHeaders headers1 = new HttpHeaders();
                        HttpEntity<String> requestEntity1 = new HttpEntity<>(headers1);
                        ResponseEntity<String> responseEntity1 = restTemplate4.exchange("http://authentication-service:8081/api/v1/authenticationService/updatePoints/" + nicknameAcquistante + "/" + (data.getCosto()), HttpMethod.PUT,requestEntity1, String.class);
                        CarteArtistiN _artista = repoA.save(new CarteArtistiN(data.getId(), data.getNome(), data.getPopolarita(), data.getGenere(), data.getImmagine(), nickname));
                        return ResponseEntity.ok(_artista);
                    }else{
                        String errorMessage = "Nick non esistente.";
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
                    }
                    }else{
                    CarteArtistiN _artista = repoA.save(new CarteArtistiN(data.getId(), data.getNome(), data.getPopolarita(), data.getGenere(), data.getImmagine(), nickname));
                    return ResponseEntity.ok(_artista);


                }


            }else{
                String errorMessage = "Punti insufficienti per acquistare questa carta.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }
        }
        @PostMapping("/acquistaCartaBrano/{nickname}")
        public ResponseEntity<?> insert_cart_Artist(@PathVariable String nickname,@RequestBody CarteInVenditaTrack data){

            RestTemplate restTemplate1 = new RestTemplate();
            ResponseEntity<Integer> pointUtente = restTemplate1.getForEntity("http://authentication-service:8081/api/v1/authenticationService/getPoints/" + nickname, Integer.class);
            if(pointUtente.getBody() > data.getCosto() && !(nickname.equals(data.getNick()))) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange("http://authentication-service:8081/api/v1/authenticationService/updatePoints/" + nickname + "/" + (-data.getCosto()), HttpMethod.PUT,requestEntity, String.class);
            if(!(data.getNick().equals("admin"))){
                RestTemplate restTemplate2 = new RestTemplate();
                ResponseEntity<String> response = restTemplate2.getForEntity("http://authentication-service:8081/api/v1/authenticationService/findByNickname/"+data.getNick(), String.class);
                if(response.getBody().equals("nickname esistente")){
                    RestTemplate restTemplate4 = new RestTemplate();
                    HttpHeaders headers1 = new HttpHeaders();
                    HttpEntity<String> requestEntity1 = new HttpEntity<>(headers1);
                    ResponseEntity<String> responseEntity1 = restTemplate4.exchange("http://authentication-service:8081/api/v1/authenticationService/updatePoints/" + data.getNick() + "/" + (data.getCosto()), HttpMethod.PUT,requestEntity1, String.class);
                    CarteBraniN _brano = repoT.save(new CarteBraniN(data.getId(), data.getNome(), data.getDurata(),data.getAnno_pubblicazione(),data.getPopolarita(), data.getImmagine(), nickname));
                    return ResponseEntity.ok(_brano);
                }else{
                    String errorMessage = "Nick non esistente.";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
                }
            }else{
                CarteBraniN _brano = repoT.save(new CarteBraniN(data.getId(), data.getNome(), data.getDurata(),data.getAnno_pubblicazione(),data.getPopolarita(), data.getImmagine(), nickname));
                return ResponseEntity.ok(_brano);


            }


        }else{
        String errorMessage = "Punti insufficienti per acquistare questa carta.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    }
    @DeleteMapping("/delete-CardArtist/{id}")
    public void delete_artist(@PathVariable String id) {
        System.out.println("Deleting artist with ID: " + id);

        List<CarteArtistiN> artista = repoA.findById(id);

        if (artista != null) {
            for (CarteArtistiN a: artista) {
                repoA.delete(a);
                System.out.println("Artist with ID " + id + " deleted successfully");
            }
        } else{
            System.out.println("Artist with ID " + id + " not found");
        }


    }

    @DeleteMapping("/delete-CardBrano/{id}")
    public void delete_track(@PathVariable String id) {
        System.out.println("Deleting artist with ID: " + id);

        List<CarteBraniN> track = repoT.findById(id);

        if (track != null) {
            for(CarteBraniN t:track) {
                repoT.delete(t);
                System.out.println("Artist with ID " + id + " deleted successfully");
            }
        } else {
            System.out.println("Artist with ID " + id + " not found");
        }

    }

    @GetMapping("/getUserArtista/{id_artista}")
    public List<CarteArtistiN> getArtisti(@PathVariable String id_artista){
        return repoA.findById(id_artista);
    }
    @GetMapping("/getUserBrano/{id_brano}")
    public List<CarteBraniN> getBrano(@PathVariable String id_brano){
        return repoT.findById(id_brano);
    }
    }



