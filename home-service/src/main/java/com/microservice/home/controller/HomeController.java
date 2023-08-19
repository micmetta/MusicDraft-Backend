package com.microservice.home.controller;

import com.microservice.home.model.GestioneAmici;
import com.microservice.home.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {


    @Autowired
    HomeRepository repository;

//    @Autowired
//    private RestTemplate restTemplate;

    @PostMapping(value = "/addAmico/{nickameU1}/{nicknameU2}")
    public String addAmico(@PathVariable String nickameU1, @PathVariable String nicknameU2){

        // nickameU1 già so che esiste perchè è il nickname dell'utente che manda la richiesta di amicizia a nicknameU2.
        // Quindi quello che devo fare è controllare innanzitutto se nel DB utenti esiste nicknameU2 e per farlo invoco
        // il metodo findByNickname(String nickname) di repository_authentication:

//        List<User> _user = repository_authentication.findByNickname(nicknameU2);

        System.out.println();
        System.out.println();
        System.out.println("SONO PRIMA DI INVOCARE restTemplate IN HomeController.");
        System.out.println();
        System.out.println();

        // SI BLOCCA NELL'ISTRUZIONE QUI SOTTO:
        //String response = restTemplate.getForObject("http://localhost:8081/api/findByNickname/" + nicknameU2, String.class);
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:8081/api/findByNickname/giacomoPoretti",
//                HttpMethod.GET,
//                null,
//                String.class
//        );

        RestTemplate restTemplate = new RestTemplate();
        //String response = restTemplate.getForObject("http://localhost:8081/api/findByNickname/giacomoPoretti", String.class); NON FUNZIONAVA..
        String response = restTemplate.getForObject("http://authentication-service:8081/api/findByNickname/" + nicknameU2, String.class);


        System.out.println();
        System.out.println();
        System.out.println("SONO DOPO L'INVOCAZIONE DI restTemplate IN HomeController.");
        System.out.println();
        System.out.println();

//        if (response.getStatusCode() == HttpStatus.OK) {
//            System.out.println("RISPOSTA OTTENUTA: ");
//            System.out.println(response.getBody());
//        } else {
//            // Gestisci l'errore in qualche modo appropriato
//            System.out.println("SI E' VERIFICATO UN ERRORE DURANTE la richiesta.");
//        }

        System.out.println();
        System.out.println();
        System.out.println("risposta ottenuta da http://authentication-service/api/findByNickname/: " + response);
        System.out.println();
        System.out.println();

        if(response.equals("nickname esistente")){
            // allora vuol dire che nicknameU2 ESISTE e quindi a questo punto posso aggiungere la coppia
            // nickameU1-nicknameU2 all'interno della tabella GestioneAmici con lo stato: "in attesa":
            repository.save(new GestioneAmici(nickameU1, nicknameU2, "in attesa"));
            return "Richiesta di amicizia inviata a " + nicknameU2;
        }
        else{
            // allora vuol dire che nicknameU2 NON ESISTE
            return "L'utente che si vuole aggiungere non esiste.";
        }
    }

}
