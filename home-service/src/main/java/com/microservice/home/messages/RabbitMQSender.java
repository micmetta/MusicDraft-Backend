package com.microservice.home.messages;


import com.microservice.home.service.GestioneScambiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// In questa classe "RabbitMQSender" si trova tutta la logica che serve
// per poter mandare un messaggio al broker RabbitMQ.
// - In questo caso ho fatto in modo che questo sender possa mandare
//   un messaggio di tipo STRING oppure di tipo JSON.
@Service
public class RabbitMQSender {

//    @Value("${rabbitmq.exchange.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String exchange;
//    @Value("${rabbitmq.exchange.name}")
//    private String exchange_json;

    @Value("${rabbitmq.exchange.update.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
    private String exchange;


//    @Value("${rabbitmq.routing.key}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String routingKey;
//    @Value("${rabbitmq.routing.json.key}")
//    private String routingJsonKey;
    @Value("${rabbitmq.routing.updatePoints.key}")
    private String routing_update_points;

    @Value("${rabbitmq.routing.updateCards.key}")
    private String routing_update_cards;


    // Il LOGGER dovrebbe andare bene sia per inviare stringhe che Json..
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSender.class);



    // Useremo rabbitTemplate per inviare i messaggi.
    // Spring Boot configura automaticamente la variabile rabbitTemplate, l'unica cosa che
    // noi dobbiamo fare è dichiararla di tipo RabbitTemplate per far sì che
    // questa configurazione avvenga.
    private final RabbitTemplate rabbitTemplate; // con questa dichiarazione sto iniettando un oggetto di tipo
    // RabbitTemplate nella classe corrente.

    // costruttore di rabbitTemplate
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    // questo metodo si preoccuperà di spedire il messaggio dato in input
    // che in questo caso è di tipo String.
//    public void sendMessage(String message){
//        LOGGER.info(String.format("Messaggio inviato -> %s", message));
//
//        // con l'istruzione di sotto setto exchange e la routingKey che fanno riferimento alla
//        // coda sulla quale voglio inviare il messaggio e infine setto il messaggio
//        // che voglio inviare.
//        rabbitTemplate.convertAndSend(exchange, routingKey, message);
//    }

    // Il metodo di sotto verrà utilizzato quando si vorrà invaire un messaggio di tipo
    // JSON invece che un messaggio di tipo String.
//    public void sendJsonMessage(GestioneScambiService scambio_accettato){
//
//        LOGGER.info(String.format("Messaggio di tipo JSON -> %s", scambio_accettato.toString()));
//
//        // con l'istruzione di sotto manderemo il messaggio "scambio_accettato" in formato JSON
//        // su exchange_json e questo exchange utilizzerà la routingJsonKey per spedire l'oggetto
//        // scambio_accettato sulla coda d'interesse.
//        // Osservazione: prima di inviare il messaggio in formato JSON però, verrà eseguita la conversione del dato e per farlo,
//        // rabbitTemplate.convertAndSend(...) invocherà automaticamente i metodi "amqpTemplate(..)" e "converter()" che ho definito
//        // nella classe "RabbitMQConfig.java".
//        rabbitTemplate.convertAndSend(exchange_json, routingJsonKey, scambio_accettato);
//        System.out.println("SONO DENTRO sendJsonMessage");
//    }


    // Metodo che invia un messaggio contenente le info necessarie per l'aggiornamento dei points all'utente di riferimento.
    public void sendUpdatePointsMessage(GestioneScambiService scambio_accettato){

        LOGGER.info(String.format("Messaggio di tipo JSON -> %s", scambio_accettato.toString()));
        rabbitTemplate.convertAndSend(exchange, routing_update_points, scambio_accettato);
        System.out.println("SONO DENTRO sendUpdatePointsMessage");
    }

    // Metodo che invia un messaggio contenente le info necessarie per l'aggiornamento delle carte all'utente di riferimento.
    public void sendUpdateCardsMessage(GestioneScambiService scambio_accettato){

        LOGGER.info(String.format("Messaggio di tipo JSON -> %s", scambio_accettato.toString()));
        rabbitTemplate.convertAndSend(exchange, routing_update_cards, scambio_accettato);
        System.out.println("SONO DENTRO sendUpdateCardsMessage");
    }

}
