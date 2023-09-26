package com.microservice.matchmaking.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.matchmaking.model.Matchmaking;
import com.microservice.matchmaking.model.RiepilogoPartitaConclusa;
import com.microservice.matchmaking.repository.MatchmakingRepository;
//import com.microservice.matchmaking.repository.MatchmakingRepositoryImplementazione;
import com.microservice.matchmaking.service.Giocatore;
import com.microservice.matchmaking.service.Mazzo;
import com.microservice.matchmaking.service.RiepPartita;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/matchmakingService/matchmakingController")
public class MatchmakingController {

    @Autowired
    MatchmakingRepository matchmaking_repository;
    static final int MAX_DIFF = 10; // differenza massima tra le popolarità dei mazzi dei giocatori che si sfideranno
    static final int POINTS_VINTI = 100; // points vinti/persi nella partita istantanea

    private final RiepPartita riepPartita;


    // Questo endpoint permette di creare una nuova partita (nella quale ci saranno solamente le info su U1 perchè U2 ancora non esiste
    // e quindi tutti gli attributi di quest'ultimo saranno a "null" (verranno passati con "null" direttamente dal frontend):
    @PostMapping(value = "/CreaNuovaPartita")
    public String crea_partita(@RequestBody Matchmaking nuova_partita){

        matchmaking_repository.save(new Matchmaking(
                nuova_partita.getNicknameU1(), nuova_partita.getNicknameU2(),
                nuova_partita.getNomemazzoU1(), nuova_partita.getNomemazzoU2(),
                nuova_partita.getPopolaritaMazzoU1(), nuova_partita.getPopolaritaMazzoU2()
        ));

        return "Partita creata con successo.";
    }


    // Endpoint che permette di cancellare una certa partita tramite il suo id dal DB:
    @DeleteMapping(value = "/CancellaPartita/{id}")
    public String cancella_partita(@PathVariable long id){
        Matchmaking partita = matchmaking_repository.findById(id);

        if(!(partita == null)){
            matchmaking_repository.delete(partita);

            return "Partita cancellata con successo.";
        }
        else{
            return "Non esiste nessuna partita con questo id.";
        }
    }


    // Questo endpoint proverà innanzitutto a trovare un match con i dati del giocatore corrente navigando le varie partite in attesa di un secondo giocatore,
    // qualora lo trovasse andrà ad aggiungere tutti i dati del giocatore corrente (che diventerà quindi il giocatore U2 della partita)
    // a questa partita già creata nella quale U1 è in attesa di un avversario.
    // Qualora non fosse trovato un match, questo endpoint creerà automaticamente una nuova partita inserendo i dati del giocatore correte come dati di U1:
    @PutMapping(value = "/CercaMatch")
    public String cerca_match(@RequestBody Giocatore giocatore) throws JsonProcessingException {

        List<Matchmaking> lista_partite = matchmaking_repository.findAll();
        boolean match_abbinato = false;
        Matchmaking info_complete_partita_match = new Matchmaking();


        if(!lista_partite.isEmpty()){

            for (Matchmaking partita: lista_partite) {

                // prendo solo le partite nelle quali non c'è il secondo utente
                // (questo indica che è una partita non ancora abbinata)
                if((partita.getNicknameU2().equals("")) && !(partita.getNicknameU1().equals(giocatore.getNicknameGiocatore())) ){

                    // controllo se le popolarità media dei mazzi dei due giocatori sono più o meno simili:
                    if(Math.abs(giocatore.getPopolaritaMazzoGiocatore() - partita.getPopolaritaMazzoU1()) <= MAX_DIFF) {

                        matchmaking_repository.delete(partita); // cancello dalla tabella matchmaking la partita corrente
                        // perchè è stato trovato un match.

                        // posso aggiungere il giocatore corrente come U2 nella partita:
                        partita.setNicknameU2(giocatore.getNicknameGiocatore());
                        partita.setNomemazzoU2(giocatore.getNomemazzoGiocatore());
                        partita.setPopolaritaMazzoU2(giocatore.getPopolaritaMazzoGiocatore());
                        match_abbinato = true;
                        info_complete_partita_match = partita;

                        break; // esco dal ciclo
                    }
                }
            }
        }

        if(match_abbinato){

            // CHIEDI A PIETRO:
            // Poichè quando un utente compra una carta dal marketplace, questa viene tolta da esso,
            // allora vuol dire che le info delle carte qui devo prenderle dalla tabella "artista" e "brani" PERCHE SOLO COSI' SONO
            // CERTO DI TROVARE LE INFO DELLE CARTE IN QUALUNQUE MOMENTO.
            // Perchè devi considerare che i riepiloghi delle partite concluse potranno
            // essere visualizzati da un utente anche dopo che questo non avrà più quelle carte e quindi le info di queste devi prenderle da una tabella
            // che sei sicuro esistere sempre dall'inizio alla fine dell'applicazione.

            // In questo caso, poichè esiste solo partita istantanea,
            // il backend calcolerà il risultato della partita, creerà l'oggetto "riepilogoPartitaConclusa"
            // e invocherà nel backend l'endpoint "/addPartitaConclusa" di RiepilogoPartitaConclusaController per salvare il risultato di questa partita.

            // oss: GRAZIE AI NOMI DEI MAZZI DEI DUE UTENTI PUOI RISALIRE ALLE INFO DELLE CARTE e salvarle nel riepilogo.

            // 1) Creo l'oggetto riepilogo_partita:

            LocalDate data_partita = LocalDate.now();
            String nickVincitore = "";
            String nicknameU1 = info_complete_partita_match.getNicknameU1();
            String nicknameU2 = info_complete_partita_match.getNicknameU2();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectMapper objectMapper_2 = new ObjectMapper();
            ObjectMapper objectMapper_3 = new ObjectMapper();
            ObjectMapper objectMapper_4 = new ObjectMapper();

            // calcolo il vincitore:
            if(info_complete_partita_match.getPopolaritaMazzoU1() > info_complete_partita_match.getPopolaritaMazzoU2()){
                nickVincitore = info_complete_partita_match.getNicknameU1();

                RestTemplate restTemplate = new RestTemplate();

                // aggiorno i points di U1 (+POINTS_VINTI)
                restTemplate.put("http://authentication-service:8081/api/v1/authenticationService/updatePoints/{nickname}/{points}",
                        null, // Nessun corpo nella richiesta
                        nicknameU1,
                        POINTS_VINTI);

                // aggiorno i points di U2 (-POINTS_VINTI) SOLO SE I POINTS CORRENTI DI QUESTO UTENTE NON SONO < 100

                int points = restTemplate.getForObject("http://authentication-service:8081/api/v1/authenticationService/getPoints/" + nicknameU2, Integer.class);
                if(points >= 100) {
                    restTemplate.put("http://authentication-service:8081/api/v1/authenticationService/updatePoints/{nickname}/{points}",
                            null, // Nessun corpo nella richiesta
                            nicknameU2,
                            -POINTS_VINTI);
                }
            } else if (info_complete_partita_match.getPopolaritaMazzoU1() < info_complete_partita_match.getPopolaritaMazzoU2()) {
                nickVincitore = info_complete_partita_match.getNicknameU2();

                RestTemplate restTemplate = new RestTemplate();

                // aggiorno i points di U1 (-POINTS_VINTI) SOLO SE I POINTS CORRENTI DI QUESTO UTENTE NON SONO < 100
                int points = restTemplate.getForObject("http://authentication-service:8081/api/v1/authenticationService/getPoints/" + nicknameU1, Integer.class);
                if(points >= 100) {
                    restTemplate.put("http://authentication-service:8081/api/v1/authenticationService/updatePoints/{nickname}/{points}",
                            null, // Nessun corpo nella richiesta
                            nicknameU1,
                            -POINTS_VINTI);
                }
                //aggiorno i points di U2 (+POINTS_VINTI)
                restTemplate.put("http://authentication-service:8081/api/v1/authenticationService/updatePoints/{nickname}/{points}",
                        null, // Nessun corpo nella richiesta
                        nicknameU2,
                        POINTS_VINTI);
            }else {
                nickVincitore = ""; // in caso di pareggio il nickVincitore sarà vuoto.
            }

            // Chiamo il l'endpoint di Pietro per avere le info di tutte le carte di entrambi i mazzi dei giocatori:
            RestTemplate restTemplate = new RestTemplate();
            List<Mazzo> mazzi_U1 = restTemplate.getForObject("http://carte-e-mazzi-service:9092/api/v1/cartemazzi/showMazzi/" + nicknameU1, List.class);
            List<Mazzo> mazzi_U2 = restTemplate.getForObject("http://carte-e-mazzi-service:9092/api/v1/cartemazzi/showMazzi/" + nicknameU2, List.class);


            System.out.println();
            System.out.println();
            System.out.println("SONO DOPO L'INVOCAZIONE DI restTemplate IN MatchmakingController.");
            System.out.println();
            System.out.println();

            System.out.println();
            System.out.println();
            System.out.println("risposta ottenuta da http://carte-e-mazzi-service:9092/api/v1/cartemazzi/showMazzi/ + nicknameU1: " + mazzi_U1);
            System.out.println("risposta ottenuta da http://carte-e-mazzi-service:9092/api/v1/cartemazzi/showMazzi/ + nicknameU2: " + mazzi_U2);
            System.out.println();

            for (Object mazzo: mazzi_U1) {
                Map<String, Object> dizionario = new LinkedHashMap<>();
                dizionario = (Map<String, Object>) mazzo;
                System.out.println("mazzo.toString() di U1: " + mazzo.toString());
                System.out.println("dizionario.get(\"id\"):");
                System.out.println(dizionario.get("id"));
            }
            System.out.println();
            for (Object mazzo: mazzi_U2) {
                Map<String, Object> dizionario = new LinkedHashMap<>();
                dizionario = (Map<String, Object>) mazzo;
                System.out.println("mazzo.toString() di U2: " + mazzo.toString());
                System.out.println("dizionario.get(\"id\"):");
                System.out.println(dizionario.get("id"));
            }

            //System.out.println("risposta ottenuta da http://carte-e-mazzi-service:9092/api/v1/cartemazzi/showMazzi/ + nicknameU1: " + mazzi_U2);
            System.out.println();
            System.out.println();

            // Adesso mi salvo in una lista tutti gli id delle carte associate al mazzo di U1:
            List<String> carteAssociateMazzoU1 = new ArrayList<>();
            for (Object mazzo: mazzi_U1){

                Map<String, Object> dizionario = new LinkedHashMap<>();
                dizionario = (Map<String, Object>) mazzo;
                if(dizionario.get("nomemazzo").equals(info_complete_partita_match.getNomemazzoU1())){
                    System.out.println("carta associata al mazzo di U1: " + (String) dizionario.get("cartaassociata"));
                    carteAssociateMazzoU1.add((String) dizionario.get("cartaassociata"));
                }
            }
            System.out.println();
            System.out.println();

            // Adesso mi salvo in una lista tutti gli id delle carte associate al mazzo di U2:
            List<String> carteAssociateMazzoU2 = new ArrayList<>();
            for (Object mazzo: mazzi_U2){

                Map<String, Object> dizionario = new LinkedHashMap<>();
                dizionario = (Map<String, Object>) mazzo;
                if(dizionario.get("nomemazzo").equals(info_complete_partita_match.getNomemazzoU2())){
                    System.out.println("carta associata al mazzo di U2: " + (String) dizionario.get("cartaassociata"));
                    carteAssociateMazzoU2.add((String) dizionario.get("cartaassociata"));
                }
            }


            // Adesso posso finalmente creare l'oggetto RiepilogoPartitaConclusaController e invocare "/addPartitaConclusa":


//            List<String> carteAssociateMazzoU1 = objectMapper.readValue(info_complete_partita_match.getCarteAssociateMazzoU1(), new TypeReference<List<String>>() {});
//            List<String> carteAssociateMazzoU2 = objectMapper_2.readValue(info_complete_partita_match.getCarteAssociateMazzoU2(), new TypeReference<List<String>>() {});
//            List<String> listaTipiCarteAssociateMazzoU1 = objectMapper_3.readValue(info_complete_partita_match.getListaTipiCarteAssociateMazzoU1(), new TypeReference<List<String>>() {});
//            List<String> listaTipiCarteAssociateMazzoU2 = objectMapper_4.readValue(info_complete_partita_match.getListaTipiCarteAssociateMazzoU2(), new TypeReference<List<String>>() {});


            RiepilogoPartitaConclusa riepilogo_partita = new RiepilogoPartitaConclusa(
                    data_partita,
                    nickVincitore,
                    info_complete_partita_match.getNicknameU1(), info_complete_partita_match.getNicknameU2(),
                    info_complete_partita_match.getNomemazzoU1(), info_complete_partita_match.getNomemazzoU2(),
                    objectMapper.writeValueAsString(carteAssociateMazzoU1), objectMapper_2.writeValueAsString(carteAssociateMazzoU2),
                    info_complete_partita_match.getPopolaritaMazzoU1(), info_complete_partita_match.getPopolaritaMazzoU2());

            // Adesso tramite la classe di servizio RiepPartita potrò eseguire gli stessi passi che esegue "/addPartitaConclusa"
            // in RiepilogoPartitaConclusaController:
//            RiepPartita riepPartita;
            riepPartita.addPartitaConclusa(riepilogo_partita); // salverà l'oggetto riepilogo_partita nella tabella RiepilogoPartitaConclusa.


            return "Matching trovato."; // infine restituirà questo
        }
        else{
            return "Matching non trovato."; // in questo caso il frontend si preoccuperà di chiamare l'endpoint "/CreaNuovaPartita" per creare una nuova partita.
        }
    }



    // Questo endpoint permette di ottenere tutte le istanze di partite nelle quali c'è un solo giocatore che ancora non è stato
    // matchato con nessuno:
    @GetMapping(value = "/getAllPartiteSenzaMatch")
    public List<Matchmaking> cerca_partite_senza_match(){

        List<Matchmaking> lista_partite = matchmaking_repository.findAll();

        System.out.println("lista_partite DOPO LA FINDall:");
        for (Matchmaking p: lista_partite) {

            System.out.println("p.getId():");
            System.out.println(p.getId());

            System.out.println("p.getNicknameU1():");
            System.out.println(p.getNicknameU1());

            System.out.println("p.getNicknameU2():");
            System.out.println(p.getNicknameU2());

            System.out.println("p.getNomemazzoU1():");
            System.out.println(p.getNomemazzoU1());

            System.out.println("p.getNomemazzoU2():");
            System.out.println(p.getNomemazzoU2());

            System.out.println("p.getPopolaritaMazzoU1():");
            System.out.println(p.getPopolaritaMazzoU1());

            System.out.println("p.getPopolaritaMazzoU2():");
            System.out.println(p.getPopolaritaMazzoU2());
        }


        List<Matchmaking> lista_partite_senza_match = new ArrayList<>();

        if(!lista_partite.isEmpty()){

            for (Matchmaking partita: lista_partite) {

                // prendo solo le partite nelle quali non c'è il secondo utente
                // (questo indica che è una partita non ancora abbinata)
                if(partita.getNicknameU2().equals("")){
                    lista_partite_senza_match.add(partita);
                }
            }
        }

        return lista_partite_senza_match; // se è VUOTA allora vuol dire che non ci sono partite non abbinate e quindi nel frontend bisognerà
        // creare una nuova partita (tramite l'endpoint "/CreaNuovaPartita") nella tabella "Matchmaking" e mettere in attesa quell'utente
        // che ha cercato la partita e ha scelto il suo mazzo con cui giocare.
    }




}
