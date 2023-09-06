package com.microservice.authentication.messages;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.authentication.service.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

// Questa classe serve per defire un consumer dei messaggi che arriveranno su una certa
// coda.
@RequiredArgsConstructor
@Component
public class RabbitMQReceiver implements RabbitListenerConfigurer{

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQReceiver.class);
    private final UpdateUser obj_user;


    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {}


    // Con il metodo di sotto possiamo prendere un certo messaggio
    // da una certa coda in base al nome di quest'ultima.
    // In sostanza stiamo sottoscrivendo questa classe ad una coda in particolare.


    //@RabbitListener(queues = {"${rabbitmq.queue.name}"})
    @RabbitListener(queues = {"${rabbitmq.queue.updatePoints.name}"})
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
            String nicknameU1 = jsonNode.get("nicknameU1").asText();
            String nicknameU2 = jsonNode.get("nicknameU2").asText();
            int points_scambiati = jsonNode.get("pointsOfferti").asInt();

            // Eseguo le azioni necessarie con i dati JSON
            System.out.println("nicknameU1: " + nicknameU1);
            System.out.println("nicknameU2: " + nicknameU2);
            System.out.println("points_scambiati: " + points_scambiati);
            System.out.println();


            // A questo punto bisogna fare questo:
//            UpdateUser obj_user = new UpdateUser();

            // 1) invoco l'endpoint "/updatePoints/{nickname}/{points}" tramite la classe service chiamata "UpdateUser" per togliere dai points
            //    di "nicknameU2" un quantitativo di points pari a "points_scambiati".
            // oss: "points_scambiati" qui sarà un valore negativo.
            String out = obj_user.aggiornamento_points(nicknameU2, -points_scambiati);
            System.out.println("ris aggiornamento points nicknameU2:" + out);
            System.out.println();

            // 2) invoco l'endpoint "/updatePoints/{nickname}/{points}" tramite la classe service chiamata "UpdateUser" per aggiungere ai points
            //    di "nicknameU1" un quantitativo di points pari a "points_scambiati".
            // oss: "points_scambiati" qui sarà un valore positivo.
            out = obj_user.aggiornamento_points(nicknameU1, points_scambiati);
            System.out.println("ris aggiornamento points nicknameU1:" + out);
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
