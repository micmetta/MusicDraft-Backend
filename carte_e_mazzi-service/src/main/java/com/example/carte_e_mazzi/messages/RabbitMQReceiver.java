package com.example.carte_e_mazzi.messages;


import com.example.carte_e_mazzi.service.SostituzioneProprietariCarte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

// Questa classe serve per defire un consumer dei messaggi che arriveranno su una certa
// coda.
@RequiredArgsConstructor
@Component
public class RabbitMQReceiver implements RabbitListenerConfigurer{

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQReceiver.class);
    // qui dovrai mettere la classe service..
    private final SostituzioneProprietariCarte sost_proprietario;


    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {}


    // Con il metodo di sotto possiamo prendere un certo messaggio
    // da una certa coda in base al nome di quest'ultima.
    // In sostanza stiamo sottoscrivendo questa classe ad una coda in particolare.

    // @RabbitListener(queues = {"${rabbitmq.queue.name}"}) // l'ho settata nell'application.proprieties di questo microservizio
    @RabbitListener(queues = {"${rabbitmq.queue.updateCards.name}"})
    public void consume(String message){

        System.out.println();
        System.out.println();
        LOGGER.info(String.format("Messaggio ricevuto -> %s", message));
        System.out.println();
        System.out.println();

        // Gestisco l'oggetto JSON ricevuto:
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> listaCarteOfferte = new ArrayList<>(); // creo una lista di stringhe per memorizzare gli id delle carte
        List<String> listaTipiCarteOfferte = new ArrayList<>();

        try {

            // Parserizzo la stringa JSON in un oggetto JsonNode
            JsonNode jsonNode = objectMapper.readTree(message);
            String nicknameU1 = jsonNode.get("nicknameU1").asText();
            String nicknameU2 = jsonNode.get("nicknameU2").asText();
            String idCartaRichiesta = jsonNode.get("idCartaRichiesta").asText();
            String tipoCartaRichiesta = jsonNode.get("tipoCartaRichiesta").asText();
            String stringaCarteOfferte = jsonNode.get("listaCarteOfferte").asText();
            String stringaTipiCarteOfferte = jsonNode.get("listaTipiCarteOfferte").asText();


            // Rimuovi i caratteri '[' e ']' iniziali e finali, quindi dividi la stringa in base alle virgole
            String[] carteArray = stringaCarteOfferte.replaceAll("\\[|\\]", "").split(",");
            String[] tipicarteArray = stringaTipiCarteOfferte.replaceAll("\\[|\\]", "").split(",");



            // Itero attraverso gli elementi dell'array carteArray e li aggiungo alla lista:
            for (String id_carta_offerta : carteArray) {
                listaCarteOfferte.add(id_carta_offerta.replaceAll("\"", "").trim()); // trim() rimuove eventuali spazi bianchi in eccesso
            }
            for (String tipo_carta_offerta : tipicarteArray) {
                listaTipiCarteOfferte.add(tipo_carta_offerta.replaceAll("\"", "").trim()); // trim() rimuove eventuali spazi bianchi in eccesso
            }



            // Eseguo le azioni necessarie con i dati JSON
            System.out.println("nicknameU1: " + nicknameU1);
            System.out.println("nicknameU2: " + nicknameU2);
            System.out.println("idCartaRichiesta: " + idCartaRichiesta);
            System.out.println("tipoCartaRichiesta: " + tipoCartaRichiesta);
            System.out.println("listaCarteOfferte: " + listaCarteOfferte); // Ora listaCarteOfferte contiene la lista di stringhe
            System.out.println("listaTipiCarteOfferte: " + listaTipiCarteOfferte); // Ora listaTipiCarteOfferte contiene la lista di stringhe
            System.out.println();


            // A questo punto, la listaCarteOfferte contiene gli ids delle carte che devono essere tolte a "nicknameU1"
            // e date a "nicknameU2" mentre "l'idCartaRichiesta", contiene l'id della carta che
            // deve essere tolta a "nicknameU2" e data a "nicknameU1", quindi:

            // RICORDA: per far funzionare correttamente questi 2 punti di sotto HAI BISOGNO DI LEGGERE PRIMA SE OGNI CARTA
            // E' DI TIPO ARTISTA O DI TIPO BRANO in modo tale da essere certo di quale metodo di "SostituzioneProprietariCarte"
            // devi chiamare perchè dipenderà da qual è la tabella a cui farai riferimento se "CarteArtistiN" o "CarteArtistiB" !!!!


            // 1) invoco l'endpoint di Pietro per sostituire "nicknameU1" con "nicknameU2" tutte le carte di presenti in "listaCarteOfferte":

            System.out.println("SONO PRIMA DI AVER AGGIORNATO il proprietario scorrendo listaCarteOfferte");
            // Per farlo uso la classe di servizio "SostituzioneProprietariCarte" e i suoi metodi.
//            for (String id_carta: listaCarteOfferte) {
//                sost_proprietario.aggiorna_proprietario(id_carta, nicknameU2);
//            }
            // listaCarteOfferte.size() == listaTipiCarteOfferte.size()
            for (int i = 0; i < listaCarteOfferte.size(); i++){
                // per ogni carta presente in listaCarteOfferte controllo il suo tipo e in base ad esso saprò in quale tabella
                // dovrò fare l'aggiornamento (artista o brano)
                if(listaTipiCarteOfferte.get(i).equals("artista")){
                    sost_proprietario.aggiorna_proprietario_cartaArtista(listaCarteOfferte.get(i), nicknameU2);
                }
                else{
                    // vuol dire che la carta è di tipo "brano"
                    sost_proprietario.aggiorna_proprietario_cartaBrano(listaCarteOfferte.get(i), nicknameU2);
                }
            }

            System.out.println("SONO DOPO AVER AGGIORNATO il proprietario scorrendo listaCarteOfferte");


            // 2) invoco l'endpoint di Pietro per sostituire "nicknameU2" con "nicknameU1" alla carta con "idCartaRichiesta":

            System.out.println("SONO PRIMA DI AVER AGGIORNATO il proprietario di idCartaRichiesta");
            //sost_proprietario.aggiorna_proprietario(idCartaRichiesta, nicknameU1);
            if(tipoCartaRichiesta.equals("artista")){
                sost_proprietario.aggiorna_proprietario_cartaArtista(idCartaRichiesta, nicknameU1);
            }
            else{
                sost_proprietario.aggiorna_proprietario_cartaBrano(idCartaRichiesta, nicknameU1);
            }
            System.out.println("SONO DOPO AVER AGGIORNATO il proprietario di idCartaRichiesta");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
