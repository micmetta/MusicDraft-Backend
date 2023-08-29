package com.microservice.home.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.microservice.home.model.GestioneAmici;
import com.microservice.home.model.GestioneScambi;
import com.microservice.home.repository.ScambiRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/homeService/scambiController")
public class ScambiController {

    @Autowired
    ScambiRepository scambi_repository;

    // Questo endpoint viene invocato dopo che l'utente (con "nicknameU1") ha selezionato nell'interfaccia la carta per la quale
    // vuole fare un'offerta al suo amico (con "nicknameU2") ed ha giò impostato la sua offerta che deve contenere questi campi avvalorati:
    // - idCartaRichiesta,
    // - listaCarteOfferte (può essere vuota oppure no),
    // - pointsOfferti (possono essere 0 oppure no),
    // in questo endpoint in corrispondenza del campo "statoOfferta" scriverò "in attesa":

    // caso da gestire: Se U1 fa un offerta che ha "listaCarteOfferte" VUOTA e  pointsOfferti==0, ALLORA L'OFFERTA VIENE AUTOMATICAMENTE
    // RIFIUTATA già nel backend perchè non considerata valida.
    // INFO: LE OFFERTE DI SCAMBIO POTRANNO ESSERE FATTE SOLAMENTE TRA AMICI, questa cosa verrà gestita direttamente nel frontend.
    @PostMapping(value = "/inviaOfferta")
    public String invia_offerta(@RequestBody GestioneScambi gestioneScambi) throws JsonProcessingException {

//        System.out.println();
//        System.out.println();
//        System.out.println("SONO ENTRATO IN /inviaOfferta");
//        System.out.println("gestioneScambi.getNicknameU1(): " + gestioneScambi.getNicknameU1());
//        System.out.println("gestioneScambi.getNicknameU2(): " + gestioneScambi.getNicknameU2());
//        System.out.println("gestioneScambi.getIdCartaRichiesta(): " + gestioneScambi.getIdCartaRichiesta());
//        System.out.println("gestioneScambi.getListaCarteOfferte():");
//        System.out.println(gestioneScambi.getListaCarteOfferte());
//        System.out.println("gestioneScambi.getPointsOfferti():");
//        System.out.println(gestioneScambi.getPointsOfferti());
//        System.out.println("gestioneScambi.getStatoOfferta(): " + gestioneScambi.getStatoOfferta());
//        System.out.println();
//        System.out.println();

        //Adesso faccio il parsing della stringa JSON listaCarteOfferte e la converto in una lista di stringhe:
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> listaCarteOfferte = objectMapper.readValue(gestioneScambi.getListaCarteOfferte(), new TypeReference<List<String>>() {});

//        System.out.println("listaCarteOfferte:");
//        System.out.println(listaCarteOfferte);
//        System.out.println("listaCarteOfferte.isEmpty():");
//        System.out.println(listaCarteOfferte.isEmpty());
//        System.out.println();
//        System.out.println();

        // PROBLEMA: I VALORI CHE PASSO IN INPUT CON POSTMAN NON VENGONO PRESI CORRETTAMENTE...
        if( (!listaCarteOfferte.isEmpty()) || (!(gestioneScambi.getPointsOfferti() == 0)) ){

            // allora vuol dire che l'offerta fatta è valida, quindi posso aggiungere alla tabella gestione_scambi la nuova riga:
            GestioneScambi nuovaOfferta = scambi_repository.save(
                                            new GestioneScambi(
                                                    gestioneScambi.getNicknameU1(),
                                                    gestioneScambi.getNicknameU2(),
                                                    gestioneScambi.getIdCartaRichiesta(),
                                                    objectMapper.writeValueAsString(listaCarteOfferte), // posso usare writeValueAsString(listaCarteOfferte) per popolare l'attributo listaCarteOfferte di GestioneScambi.
                                                    gestioneScambi.getPointsOfferti(),
                                                    "in_attesa", // non me lo faccio passare dal frontend ma lo aggiungo direttamente nel backend.
                                                    0, // Imposto idStart a 0 temporaneamente.
                                                    0 // numControfferta all'inizio l'ha imposto su 0.
                                            ));

            // Dopo il salvataggio, imposto idStart uguale all'id generato automaticamente
            nuovaOfferta.setIdStart(nuovaOfferta.getId());
            scambi_repository.save(nuovaOfferta);

            return "Offerta inviata.";
        }
        else{
            return "Offerta non valida.";
        }
    }

    // Questo endpoint permette di vedere tutte le richieste in attesa di un certo utente attraverso il suo nickname:
    @GetMapping(value = "/getAllOfferte/{nickname}")
    public List<GestioneScambi> get_allOfferte(@PathVariable String nickname){

        List<GestioneScambi> offerte_ricevute = scambi_repository.findByNicknameU2(nickname);

        // QUESTO IF NON SERVE PUOI TOGLIERLO..
        if(!offerte_ricevute.isEmpty()){
            // vuol dire che l'utente ha ricevuto delle offerte e gliele restituisco tutte.
            return offerte_ricevute;
        }
        else{
            // vuol dire che l'utente corrente non ha ricevuto nessuna offerta.
            return offerte_ricevute; // sarà vuota.
        }
    }

    // MOSTRA TUTTE LE OFFERTE INVIATE DA UN CERTO UTENTE attraverso il suo nickname..

    ////////////////////////////////////////////////////////


    // Adesso devi implementare l'accetta, il rifiuta e la controfferta di una certa offerta ricevuta da parte di un utente con nicknameU2
    // presente nella tabella gestione_scambi !!!!!!!!!!!!!!!!

    // Endpoint che gestisce l'accettazione di nicknameU2 di un'offerta fattagli da nicknameU1

    //////////////////////////////////////////////////////////////////////////////////////////


    // Endpoint che gestisce il rifiuto o più in generale la cancellazione di una certa offerta che ha un certo id univoco.
    // Per accedere all'offerta specifica utilizzerò il suo id.
    @DeleteMapping(value = "/cancellaOffertaTramiteId/{id}")
    public String cancella_offerta_con_id(@PathVariable long id){

        GestioneScambi offerta_rifiutata = scambi_repository.findById(id);

        if(offerta_rifiutata != null){
            // se entro qui vuol dire che devo cancellare la riga rappresentata da "offerta_rifiutata" dalla tabella "gestione_scambi":
            scambi_repository.delete(offerta_rifiutata);

            return "Offerta cancellata con successo.";
        }
        else{
            return "Non esiste nessuna offerta con questo id.";
        }

    }


    // Endpoint che gestisce il rifiuto o più in generale la cancellazione di una certa offerta che ha un certo idStart.
    // Per accedere all'offerta specifica utilizzerò il suo idStart.
    @DeleteMapping(value = "/cancellaOffertaTramiteIdStart/{idStart}")
    public String cancella_offerta_con_idStart(@PathVariable long idStart){

        GestioneScambi offerta_rifiutata = scambi_repository.findById(idStart);

        if(offerta_rifiutata != null){
            // se entro qui vuol dire che devo cancellare la riga rappresentata da "offerta_rifiutata" dalla tabella "gestione_scambi":
            scambi_repository.delete(offerta_rifiutata);

            return "Offerta cancellata con successo.";
        }
        else{
            return "Non esiste nessuna offerta con questo idStart.";
        }

    }


    // Endpoint che gestisce una controfferta che può essere fatta da parte di nicknameU2 verso nicknameU1
    // o viceversa.
    // - Per navigare tra le controfferte utilizzerò l'idStart.
    @PutMapping(value = "/controfferta")
    public String modifica_offerta(@RequestBody GestioneScambi gestioneScambi) throws JsonProcessingException {

        GestioneScambi controfferta = new GestioneScambi();
        List<GestioneScambi> controfferte = scambi_repository.findByIdStartAndStatoOfferta(gestioneScambi.getIdStart(), "controfferta");
        int maxNumControfferta = 0;

        // Poichè la controfferta che sto facendo adesso potrebbe non essere la prima ma solo l'ultima di altre controfferte precedenti,
        // per essere certo di settare controfferta.setNumControfferta(..) al numero corretto, devo prima individuare il maxNumControfferta
        // dell'offerta corrente:
        for (GestioneScambi controfferta_corrente : controfferte) {
            if (controfferta_corrente.getNumControfferta() > maxNumControfferta) {
                maxNumControfferta = controfferta_corrente.getNumControfferta(); // alla fine del for sono certo di avere nella variabile maxNumControfferta
                // il valore che mi interessa e che incrementerò di uno nella chiamata di sotto a controfferta.setNumControfferta(..)
            }
        }


        controfferta.setNicknameU1(gestioneScambi.getNicknameU1());
        controfferta.setNicknameU2(gestioneScambi.getNicknameU2());
        controfferta.setIdCartaRichiesta(gestioneScambi.getIdCartaRichiesta());
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> listaCarteOfferte = objectMapper.readValue(gestioneScambi.getListaCarteOfferte(), new TypeReference<List<String>>() {});
        controfferta.setListaCarteOfferte(objectMapper.writeValueAsString(listaCarteOfferte));
        controfferta.setPointsOfferti(gestioneScambi.getPointsOfferti());
        controfferta.setStatoOfferta("controfferta");
        controfferta.setIdStart(gestioneScambi.getIdStart()); // rimane invariato.
        controfferta.setNumControfferta(maxNumControfferta + 1); // la incremento di 1.


        // Aggiungo la controfferta appena creata nella tabella "GestioneScambi":
        scambi_repository.save(
                new GestioneScambi(
                        controfferta.getNicknameU1(),
                        controfferta.getNicknameU2(),
                        controfferta.getIdCartaRichiesta(),
                        controfferta.getListaCarteOfferte(), // posso usare writeValueAsString(listaCarteOfferte) per popolare l'attributo listaCarteOfferte di GestioneScambi.
                        controfferta.getPointsOfferti(),
                        controfferta.getStatoOfferta(), // non me lo faccio passare dal frontend ma lo aggiungo direttamente nel backend.
                        controfferta.getIdStart(), // Imposto idStart a 0 temporaneamente.
                        controfferta.getNumControfferta() // numControfferta all'inizio l'ha imposto su 0.
                ));

        return "Controfferta inviata.";
    }


    // Questo endpoint permette ad un certo utente di poter vedere qual è lo storico di tutte le controfferte che sono state fatte per
    // una specifica offerta iniziale (tramite l'id univoco di quest'ultima) che può essere stata inviata inizialmente da lui oppure no.
    // - Per ottenere lo storico mi basta navigare tra le controfferte che hanno l'idStart uguale all'id univoco dell'offerta iniziale
    //   che viene dato in input.
    @GetMapping(value = "getStorico/{id}")
    public List<GestioneScambi> get_storico(@PathVariable long id){

        List<GestioneScambi> storico_offerta = scambi_repository.findByIdStartAndStatoOfferta(id, "controfferta");

        // QUESTO IF NON SERVE PUOI TOGLIERLO..
        if(!storico_offerta.isEmpty()){
            return storico_offerta; // non sarà vuota e conterrà tutte le controfferte di quell'offerta particolare.
        }
        else{
            return storico_offerta; // sarà vuota e questo vuol dire che per l'offerta inserita non ci sono state controfferte.
        }
    }


    // Qui fai l'endpoint per ottenere solo l'ultima controfferta (facente parte di una certa offerta iniziale)
    // ricevuta da un certo utente.
    @GetMapping(value = "getUltimaControfferta/{idStart}")
    public GestioneScambi get_ultima_controfferta(@PathVariable long idStart){

        List<GestioneScambi> controfferte = scambi_repository.findByIdStartAndStatoOfferta(idStart, "controfferta");
        GestioneScambi ultimaControfferta = null;
        int maxNumControfferta = 0;


        // questo IF NON SERVE PUOI TOGLIERLO...
        if(!controfferte.isEmpty()) {
            for (GestioneScambi controfferta : controfferte) {
                if (controfferta.getNumControfferta() > maxNumControfferta) {
                    maxNumControfferta = controfferta.getNumControfferta();
                    ultimaControfferta = controfferta;
                }
            }

            return ultimaControfferta;
        }
        else{
            return ultimaControfferta; // sarà vuota perchè vuol dire che non ci sono state controfferte.
        }
    }


    // Questo endpoint permette ad un utente di accettare un'offerta/controfferta.
    // Quando questo avverrà:.
    // 1) verrà invocato RabbitMQ per aggiornare la tabella delle carte in base alle info dell'offerta che si è conclusa.
    // 2) verranno eliminate dalla tabella GestioneScambi tutte le righe che facevano riferimento a questa offerta

    @PutMapping(value = "accettaOfferta/{id}")
    public String accetta_offerta(@PathVariable long id){

        GestioneScambi offerta_accettata = scambi_repository.findById(id);

        if(offerta_accettata != null) {

            // 1) Innanzitutto, verrà invocato RabbitMQ per aggiornare la tabella delle carte in base alle info dell'offerta che si è conclusa.
            // Innanzitutto prendo il valore del campo numControfferta perchè:
            // - Se esso è pari allora vuol dire che l'utente che ha accettato l'offerta è quello a cui era stata inviata la primissima offerta.
            // - Altrimenti, se è dispari allora vuol dire che l'utente che ha accettato l'offerta è quello che invece aveva inviato la
            //  primissa offerta.
            int num_controfferta = offerta_accettata.getNumControfferta();

            if (num_controfferta % 2 == 0) {

                // num_controfferta pari
                System.out.println();
                System.out.println();
                System.out.println("Invoco RabbitMQ e gli passo questi dati di aggiornamento: ");
                System.out.println(offerta_accettata);
                System.out.println("In particolare, " +
                        "la listaCarteOfferte contiene gli ids delle carte che devono essere tolte a " + offerta_accettata.getNicknameU1() +
                        " e date a " + offerta_accettata.getNicknameU2() + " mentre l'idCartaRichiesta, contiene l'id della carta che " +
                        "deve essere tolta a " + offerta_accettata.getNicknameU2() + " e data a " + offerta_accettata.getNicknameU1());
                System.out.println();
                System.out.println();

            } else {
                // num_controfferta dispari
                System.out.println();
                System.out.println();
                System.out.println("Invoco RabbitMQ e gli passo questi dati di aggiornamento: ");
                System.out.println(offerta_accettata);
                System.out.println("In particolare, " +
                        "la listaCarteOfferte contiene gli ids delle carte che devono essere tolte a " + offerta_accettata.getNicknameU2() +
                        " e date a " + offerta_accettata.getNicknameU1() + " mentre l'idCartaRichiesta, contiene l'id della carta che " +
                        "deve essere tolta a " + offerta_accettata.getNicknameU2() + " e data a " + offerta_accettata.getNicknameU1());
                System.out.println();
                System.out.println();
            }

            // 2) Verranno eliminate dalla tabella GestioneScambi tutte le righe che facevano riferimento a questa offerta, ovvero
            // che avevano come idStart l'idStart di offerta_accettata:
            List<GestioneScambi> righe_da_cancellare = scambi_repository.findByIdStart(offerta_accettata.getIdStart());
            for (GestioneScambi riga_da_cancellare: righe_da_cancellare) {
                scambi_repository.delete(riga_da_cancellare);
            }

            return "Offerta accettata con successo";
        }
        else{
            return "Errore: Non c'è nessuna offerta da accettare che ha l'id dato in input.";
        }

    }

    // GLI ENDPOINTS INSERITI QUI DENTRO FUNZIONANO.. ADESSO DEVI FARE LA PUSH E POI DOMANI MATTINA PARLA CON PIETRO PER DIRGLI CHE IN QUESTO MICROSERVIZIO
    // CONVIENE USARE RABBITMQ.

    // Quello che dovrebbe succedere è questo:
    // - Nel momento in cui viene accettata una proposta, il microservizio home-service richiama RabbitMQ passandogli tutti i dati necessari
    //   per poter eseguire correttamente la modifica nella tabella carte fatta da Pietro. A questo punto RabbitMQ manderà l'aggiornamento
    //   al microservizio fatto da Pietro che si preoccupa di gestire la tabella delle carte (assegnate agli utenti) passandogli tutti i dati
    //   necessari all'aggiornamento.

    // - Per quanto riguarda invece eventuali points aggiuntivi presenti nello scambio, il microservizio home-service anche senza l'utilizzo di
    //   RabbitMQ in questo caso, può invocare un endpoint di authentication-service per settare i points aggiuntivi all'utente di riferimento.

    //   Al termine di tutto questo processo le carte scambiate (+ eventuali points aggiuntivi) saranno assegnate agli utenti giusti.
    //

}
