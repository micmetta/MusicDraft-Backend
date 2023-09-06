package com.example.carte_e_mazzi.messages;


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

// Questa classe serve per defire un consumer dei messaggi che arriveranno su una certa
// coda.
@RequiredArgsConstructor
@Component
public class RabbitMQReceiver implements RabbitListenerConfigurer{

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQReceiver.class);
    // qui dovrai mettere la classe service..

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

        try {
            // Parsa la stringa JSON in un oggetto JsonNode
            JsonNode jsonNode = objectMapper.readTree(message);

            // Ora puoi accedere ai campi del JSON come descritto nella risposta precedente
            int id = jsonNode.get("id").asInt();
            String nicknameU1 = jsonNode.get("nicknameU1").asText();
            String nicknameU2 = jsonNode.get("nicknameU2").asText();
            String idCartaRichiesta = jsonNode.get("idCartaRichiesta").asText();
            String listaCarteOfferte = jsonNode.get("listaCarteOfferte").asText();

            // Eseguo le azioni necessarie con i dati JSON
            System.out.println("nicknameU1: " + nicknameU1);
            System.out.println("nicknameU2: " + nicknameU2);
            System.out.println("idCartaRichiesta: " + idCartaRichiesta);
            System.out.println("listaCarteOfferte: " + listaCarteOfferte);
            System.out.println();


            // A questo punto, la listaCarteOfferte contiene gli ids delle carte che devono essere tolte a "nicknameU1"
            // e date a "nicknameU2" mentre "l'idCartaRichiesta", contiene l'id della carta che
            // deve essere tolta a "nicknameU2" e data a "nicknameU1", quindi:

            // 1) invoco l'endpoint di Pietro per cancellare tutte le carte di "nicknameU1" presenti in "listaCarteOfferte".


            // 2) invoco l'endpoint di Pietro per aggiungere alle di carte "nicknameU2" tutte quelle presenti in "listaCarteOfferte".


            // 3) invoco l'endpoint di Pietro per togliere a "nicknameU2" la carta con "idCartaRichiesta".


            // 4) invoco l'endpoint di Pietro per aggiungere alle carte di "nicknameU1" la carta con "idCartaRichiesta".



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
