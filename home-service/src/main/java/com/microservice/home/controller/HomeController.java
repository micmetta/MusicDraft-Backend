package com.microservice.home.controller;

import com.microservice.home.model.GestioneAmici;
import com.microservice.home.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    HomeRepository repository;

    // servizio che permette di fare una richiesta di amicizia e di metterla nello stato di attesa.
    @PostMapping(value = "/addAmico/{nicknameU1}/{nicknameU2}")
    public String addAmico(@PathVariable String nicknameU1, @PathVariable String nicknameU2){

        // nickameU1 già so che esiste perchè è il nickname dell'utente che manda la richiesta di amicizia a nicknameU2.
        // Quindi quello che devo fare è controllare innanzitutto se nel DB utenti esiste nicknameU2 e per farlo invoco
        // il metodo findByNickname(String nickname) di repository_authentication:

        System.out.println();
        System.out.println();
        System.out.println("SONO PRIMA DI INVOCARE restTemplate IN HomeController.");
        System.out.println();
        System.out.println();

        // PRIMA CONTROLLO SE E' GIA' PRESENTE UNA COPPIA nicknameU1-nicknameU2 in Gestione richieste, in questo modo
        // non permetto all'utente U1 di mandare un'altra richiesta allo stesso utente:
        GestioneAmici amiciziaDaAccettare = repository.findByNicknameU1AndNicknameU2(nicknameU1, nicknameU2);
        GestioneAmici amiciziaDaAccettare_da_U2_a_U1 = repository.findByNicknameU1AndNicknameU2(nicknameU2, nicknameU1);

        if(nicknameU1.equals(nicknameU2)){
            return "Non puoi inviare una richiesta di amicizia a te stesso!";
        }
        else {
            if (amiciziaDaAccettare_da_U2_a_U1 != null) {
                // se entro qui vuol dire che nella tabella gestione_amici ho già una riga nicknameU2-nicknameU1 e quindi
                // non permetto a U1 in questo caso di mandare la richiesta ad U2 perchè sono già amici.
                return "Sei già amico di questo utente.";
            } else if (amiciziaDaAccettare != null) {
                // vuol dire che è stata già fatta una richiesta da U1 a U2 e quindi non gli permetto di farne un'altra:
                if (amiciziaDaAccettare.getStato().equals("in attesa"))
                    return "Richiesta di amicizia già inviata, attendi che l'altro utente risponda.";
                else {
                    return "Sei già amico di questo utente.";
                }
            } else {
                RestTemplate restTemplate = new RestTemplate();
                //String response = restTemplate.getForObject("http://localhost:8081/api/findByNickname/giacomoPoretti", String.class); NON FUNZIONAVA..
                String response = restTemplate.getForObject("http://authentication-service:8081/api/findByNickname/" + nicknameU2, String.class);

                System.out.println();
                System.out.println();
                System.out.println("SONO DOPO L'INVOCAZIONE DI restTemplate IN HomeController.");
                System.out.println();
                System.out.println();

                System.out.println();
                System.out.println();
                System.out.println("risposta ottenuta da http://authentication-service/api/findByNickname/: " + response);
                System.out.println();
                System.out.println();

                if (response.equals("nickname esistente")) {
                    // allora vuol dire che nicknameU2 ESISTE e quindi a questo punto posso aggiungere la coppia
                    // nickameU1-nicknameU2 all'interno della tabella GestioneAmici con lo stato: "in attesa":
                    repository.save(new GestioneAmici(nicknameU1, nicknameU2, "in attesa"));
                    return "Richiesta di amicizia inviata a " + nicknameU2;
                } else {
                    // allora vuol dire che nicknameU2 NON ESISTE
                    return "L'utente che si vuole aggiungere non esiste.";
                }
            }
        }
    }


    // servizio che permette all'utente (già loggato nel sistema) tramite il suo nickname
    // di sapere quanti richieste di amicizia ci sono in attesa verso di lui:
    @GetMapping(value = "/richiesteInAttesa/{nickname}")
    public int getNumRichieste(@PathVariable String nickname){

        String stato = "in attesa";
        int num_richieste_attesa = 0;
        List<GestioneAmici> lista_gestioneAmici = repository.findByNicknameU2AndStato(nickname, stato);

        if(!lista_gestioneAmici.isEmpty()){
            // allora vuol dire che questo utente ha almeno una richiesta di amicizia "in attesa" e quindi le conto:
            num_richieste_attesa = lista_gestioneAmici.size();
        }
        return num_richieste_attesa;
    }

    // servizio che permette all'utente (già loggato nel sistema) tramite il suo nickname
    // di sapere quali sono le richieste di amicizia che sono in attesa verso di lui.
    // Quindi per ogni richiesta in attesa comparirà anche il nickname dell'utente che gliel'ha mandata:
    @GetMapping(value = "/richiesteInAttesaUtenti/{nickname}")
    public List<String> get_ListaUtentiRichiesteAmicizia(@PathVariable String nickname){

        String stato = "in attesa";
        List<String> lista_utenti_richiedenti = new ArrayList<>();
        List<GestioneAmici> richieste_in_attesa = repository.findByNicknameU2AndStato(nickname, stato);

        if(!richieste_in_attesa.isEmpty()) {
            for (GestioneAmici richiesta : richieste_in_attesa) {
                lista_utenti_richiedenti.add(richiesta.getNicknameU1());
            }
        }

        return lista_utenti_richiedenti; // se non è entrato nell'if precedente restituirà null
    }


    // Implementazione del servizio REST che verrà invocato quando l'utente (a cui FACCIO RIFERIMENTO con la variabile nickameU2
    // in input a questo servizio) cliccherà su un pulsante per accettare una richiesta di amicizia.
    // Quindi in sostanza il servizio permette di far passare una richiesta di amicizia dallo stato "in attesa" allo stato "accettata".
    @PutMapping(value = "/richiestaAccettata/{nicknameU1}/{nicknameU2}")
    public String accetta_richiesta(@PathVariable String nicknameU1, @PathVariable String nicknameU2){

        System.out.println();
        System.out.println();
        System.out.println("SONO ENTRATO IN accetta_richiesta.");
        System.out.println();
        System.out.println();

        // aggiorno la tabella gestioneAmici in corrispondenza della riga d'interesse andando a
        // sovrascrivere lo stato "in attesa" con lo stato "accettata".
        String statoAccettata = "accettata";
        GestioneAmici amiciziaDaAccettare = repository.findByNicknameU1AndNicknameU2(nicknameU1, nicknameU2);

        if(amiciziaDaAccettare != null){
            if(amiciziaDaAccettare.getStato().equals("in attesa")) {
                amiciziaDaAccettare.setStato(statoAccettata);
                repository.save(amiciziaDaAccettare);

                return "richiesta accettata.";
            }
            else{
                return "La richiesta di amicizia è già stata accettata.";
            }
        }
        else{
            return "si è verificato un errore durante l'accettazione della richiesta perchè - amiciziaDaAccettare - è null.";
        }
    }

}
