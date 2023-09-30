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
@RequestMapping("/api/v1/homeService")
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
                String response = restTemplate.getForObject("http://authentication-service:8081/api/v1/authenticationService/findByNickname/" + nicknameU2, String.class);

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


    // Endpoint che permette ad un utente di rifiutare una certa richiesta di amicizia che ha ricevuto:

    // - aggiunta dopo che PIETRO HA FATTO FUNZIONARE KUBERNETIS..
    @DeleteMapping(value = "/richiestaRifiutata/{nicknameU1}/{nicknameU2}")
    public String rifiuta_richiesta(@PathVariable String nicknameU1, @PathVariable String nicknameU2){

        // aggiorno la tabella gestioneAmici in corrispondenza della riga d'interesse andandola a cancellare dalla tabella:
        GestioneAmici amiciziaDaRifiutare = repository.findByNicknameU1AndNicknameU2(nicknameU1, nicknameU2);

        if(amiciziaDaRifiutare != null){
            if(amiciziaDaRifiutare.getStato().equals("in attesa")) {
                repository.delete(amiciziaDaRifiutare);
                return "richiesta rifiutata.";
            }
            else{
                return "La richiesta di amicizia è già stata rifiutata.";
            }
        }
        else{
            return "si è verificato un errore durante l'accettazione della richiesta perchè - amiciziaDaRifiutare - è null.";
        }
    }


    // Endpoint che permette ad un utente di cancellare una certa amicizia che aveva con un altro utente:

    // - aggiunta dopo che PIETRO HA FATTO FUNZIONARE KUBERNETIS..
    @DeleteMapping(value = "/cancellaAmicizia/{nicknameU1}/{nicknameU2}")
    public String cancella_amicizia(@PathVariable String nicknameU1, @PathVariable String nicknameU2){

        // aggiorno la tabella gestioneAmici in corrispondenza della riga d'interesse andandola a cancellare dalla tabella:
        GestioneAmici amiciziaDaCancellare = repository.findByNicknameU1AndNicknameU2(nicknameU1, nicknameU2);
        GestioneAmici amiciziaDaCancellare_contrario = repository.findByNicknameU1AndNicknameU2(nicknameU2, nicknameU1);

        if(amiciziaDaCancellare != null){
            if(amiciziaDaCancellare.getStato().equals("accettata")) {
                repository.delete(amiciziaDaCancellare);
                return "amicizia cancellata.";
            }
            else{
                return "Tu e questo utente già non siete amici.";
            }
        }
        else if(amiciziaDaCancellare_contrario != null){

            if(amiciziaDaCancellare_contrario.getStato().equals("accettata")) {
                repository.delete(amiciziaDaCancellare_contrario);
                return "amicizia cancellata.";
            }
            else{
                return "Tu e questo utente già non siete amici.";
            }
        }
        else{
            return "si è verificato un errore durante la cancellazione dell'amicizia perchè - amiciziaDaCancellare e amiciziaDaCancellare_contrario- sono null.";
        }

    }




    // Questo endpoint mi permette di poter prendere tutti gli amici di un certo utente il cui nickname viene dato in input.
    @GetMapping(value = "/getAllFriends/{nickname}")
    public List<String> get_allAmici(@PathVariable String nickname){
        List<GestioneAmici> lista_amici_lettura_coppie_da_sinistra_a_destra = repository.findByNicknameU1AndStato(nickname, "accettata");
        List<GestioneAmici> lista_amici_lettura_coppie_da_destra_a_sinistra = repository.findByNicknameU2AndStato(nickname, "accettata");
        List<String> risposta = new ArrayList<>();

        if(lista_amici_lettura_coppie_da_sinistra_a_destra.isEmpty() && lista_amici_lettura_coppie_da_destra_a_sinistra.isEmpty()){
            return risposta; // sarà [] in questo caso perchè l'utente non ha amici
        }
        else{
            // Allora inserisco in risposta tutti gli amici che ha questo utente:

            if(!lista_amici_lettura_coppie_da_sinistra_a_destra.isEmpty()){
                // qui considero nickname come utente di sinistra della coppia e quindi il suo amico si troverà a destra per questo prendo
                // il getNicknameU2
                for (GestioneAmici amico : lista_amici_lettura_coppie_da_sinistra_a_destra) {
                    risposta.add(amico.getNicknameU2());
                }
            }
            if(!lista_amici_lettura_coppie_da_destra_a_sinistra.isEmpty()) {
                // qui invece considero nickname come utente di destra della coppia e quindi il suo amico si troverà a sinistra per questo prendo
                // il getNicknameU1
                for (GestioneAmici amico : lista_amici_lettura_coppie_da_destra_a_sinistra) {
                    risposta.add(amico.getNicknameU1());
                }
            }

            return risposta;

        }
    }

    // Questo endpoint serve per ottenere tutti gli utenti che sono online e che sono amici di un certo utente a cui faccio riferimento tramite
    // il suo nickname.
    @GetMapping(value = "/getAllFriendsAreOnline/{nickname}")
    public List<String> get_friendsAreOnline(@PathVariable String nickname){

        List<GestioneAmici> lista_amici_lettura_coppie_da_sinistra_a_destra = repository.findByNicknameU1AndStato(nickname, "accettata");
        List<GestioneAmici> lista_amici_lettura_coppie_da_destra_a_sinistra = repository.findByNicknameU2AndStato(nickname, "accettata");
        List<String> amici_online = new ArrayList<>();

        if((lista_amici_lettura_coppie_da_sinistra_a_destra.isEmpty() && lista_amici_lettura_coppie_da_destra_a_sinistra.isEmpty())){

            // vuol dire che l'utente richiedente non ha amici E QUINDI amici_online non conterrà nessun elemento
            return amici_online; // sarà [] in questo caso
        }
        else{
            
            // vuol dire che l'utente richiedente ha degli amici e quindi devo andare a vedere quali di questi sono attualmente online:
            if(!lista_amici_lettura_coppie_da_sinistra_a_destra.isEmpty()){

                // Qui considero nickname come utente di sinistra della coppia e quindi il suo amico si troverà a destra per questo prendo
                // il getNicknameU2:
                for (GestioneAmici amico : lista_amici_lettura_coppie_da_sinistra_a_destra) {

                    // Adesso per sapere se l'amico corrente è online o meno devo chiamare l'endpoint: @GetMapping("/getIsOnline/{nickname}")
                    // passandogli come {nickname} il nickname dell'amico corrente:
                    RestTemplate restTemplate = new RestTemplate();
                    String amico_corrente_isOnline = restTemplate.getForObject("http://authentication-service:8081/api/v1/authenticationService/getIsOnline/" + amico.getNicknameU2(), String.class);

                    System.out.println();
                    System.out.println();
                    System.out.println("SONO in /getAllFriendsAreOnline/{nickname} IN GestioneAmici amico : lista_amici_lettura_coppie_da_sinistra_a_destra");
                    System.out.println("amico_corrente_isOnline: " + amico_corrente_isOnline);
                    System.out.println();
                    System.out.println();

                    if(amico_corrente_isOnline.equals("Online")){
                        amici_online.add(amico.getNicknameU2());
                    } else if (amico_corrente_isOnline.equals("Utente inserito inesistente.")) {

                        // se entro qui vuol dire che amico.getNicknameU2() è inesistente nella tabella user per qualche motivo..
                        amici_online.add("ERRORE, amico.getNicknameU2(): " + amico.getNicknameU2() + " è INESISTENTE in user");
                    }
                }
            }

            if(!lista_amici_lettura_coppie_da_destra_a_sinistra.isEmpty()){

                // Qui invece considero nickname come utente di destra della coppia e quindi il suo amico si troverà a sinistra per questo prendo
                // il getNicknameU1:
                for (GestioneAmici amico : lista_amici_lettura_coppie_da_destra_a_sinistra){

                    // Adesso per sapere se l'amico corrente è online o meno devo chiamare l'endpoint: @GetMapping("/getIsOnline/{nickname}")
                    // passandogli come {nickname} il nickname dell'amico corrente:
                    RestTemplate restTemplate = new RestTemplate();
                    String amico_corrente_isOnline = restTemplate.getForObject("http://authentication-service:8081/api/v1/authenticationService/getIsOnline/" + amico.getNicknameU1(), String.class);

                    System.out.println();
                    System.out.println();
                    System.out.println("SONO in /getAllFriendsAreOnline/{nickname} IN GestioneAmici amico : lista_amici_lettura_coppie_da_destra_a_sinistra");
                    System.out.println("amico_corrente_isOnline: " + amico_corrente_isOnline);
                    System.out.println();
                    System.out.println();


                    if(amico_corrente_isOnline.equals("Online")){
                        amici_online.add(amico.getNicknameU1());
                    } else if (amico_corrente_isOnline.equals("Utente inserito inesistente.")) {

                        // se entro qui vuol dire che amico.getNicknameU2() è inesistente nella tabella user per qualche motivo..
                        amici_online.add("ERRORE, amico.getNicknameU1(): " + amico.getNicknameU1() + " è INESISTENTE in user");
                    }
                }
            }

            if(amici_online.isEmpty()){
                amici_online.add("Nessun amico online.");
            }

            // SE NESSUN AMICO E' ONLINE ALLORA amici_online avrà un solo elemento e in particolare
            // amici_online[0] == "Nessun amico online."
            // ALTRIMENTI, amici_online conterrà tutta la lista degli amici online dell'utente richiedente.
            return amici_online;
        }

    }




}
