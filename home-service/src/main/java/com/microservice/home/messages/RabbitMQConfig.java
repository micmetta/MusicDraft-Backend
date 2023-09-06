package com.microservice.home.messages;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// PROVA PRIMA AD USARE UN SOLO EXCHANGE e vedi se funziona..
// DOPO PROVA A CREARE UN SENDER A PARTE..

// Il producer manda un messaggio su un exchange, dopodichè quest'ultimo
// tramite l'utilizzo di una certa routing key inoltra questo messaggio
// su una certa coda alla quale potrà essere iscritto uno o più consumer
// che riceveranno il messaggio.
// - Il binding tra l'exchange e una certa coda avverrà tramite una certa
//   routing key.
@Configuration
public class RabbitMQConfig {

//    @Value("${rabbitmq.queue.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String queue;
//
//    @Value("${rabbitmq.queue.json.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String jsonQueue; // questa è la coda che riceve solo messaggi JSON

    @Value("${rabbitmq.queue.updatePoints.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
    private String queue_update_points;

    @Value("${rabbitmq.queue.updateCards.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
    private String queue_update_cards;


    @Value("${rabbitmq.exchange.update.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
    private String exchange;



    // questo qui sotto è il routing_key della coda che accetta solo messaggi di tipo JSON
//    @Value("${rabbitmq.routing.json.key}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String routing_json_key;
    @Value("${rabbitmq.routing.updatePoints.key}")
    private String routingUpdatePointsKey;

    @Value("${rabbitmq.routing.updateCards.key}")
    private String routingUpdateCardssKey;


    // spring bean for rabbitmq queue
    // la coda qui sotto è quella che riceve solo messaggi in formato STRING
//    @Bean
//    public Queue queue(){
//        return new Queue(queue); // setto il nome della coda
//    }


    // la coda qui sotto è quella che riceve solo messaggi in formato JSON
//    @Bean
//    public Queue jsonQueue(){
//        return new Queue(jsonQueue);
//    }


    // la coda qui sotto è quella che si preoccupa di mandare solo i messaggi tramite RABBITmq
    // per l'aggiornamento dei points all' authentication-service:
    @Bean
    public Queue updatePointsQueue() {
        return new Queue(queue_update_points);
    }

    @Bean
    public Queue updateCardsQueue() {
        return new Queue(queue_update_cards);
    }



    // spring bean for rabbitmq exchange (topic sia per la coda che manda stringhe che per quella che manda json)
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }


    // topic per la coda di aggiornamento points
//    @Bean
//    public TopicExchange exchange_update_points() { return new TopicExchange(exchange_update_points); }



    // Con il metodo qui sotto possiamo collegare tra loro
    // la coda definita sopra con l'exchange definito sopra
    // attraverso una certa routingKey che in questo caso è rappresentata
    // dalla stringa "javaguides_routing_key" salvata nella variabile routing_key.
//    @Bean
//    public Binding binding(){
//        return BindingBuilder
//                .bind(queue())
//                .to(exchange())
//                .with(routing_key);
//    }

    // Con il metodo qui sotto possiamo collegare tra loro
    // la coda che accetta solo messaggi di tipo JSON definita sopra
    // con l'exchange che rimane sempre lo stesso
    // attraverso una certa routingKey che in questo caso è rappresentata
    // dalla stringa "javaguides_routing_json_key" salvata nella variabile routing_json_key.
//    @Bean
//    public Binding jsonBinding(){
//        return BindingBuilder
//                .bind(queue())
//                .to(exchange()) // è lo stesso del metodo binding di sopra
//                .with(routing_json_key);
//    }


    // binding per la coda che aggiorna i points
    @Bean Binding updatePointsBinding(){
        return BindingBuilder
                .bind(updatePointsQueue())
                .to(exchange())
                .with(routingUpdatePointsKey);
    }

    // binding per la coda che aggiorna le carte
    @Bean Binding updateCardsBinding(){
        return BindingBuilder
                .bind(updateCardsQueue())
                .to(exchange())
                .with(routingUpdateCardssKey);
    }


    // Questo metodo qui sotto è necessario quando vogliamo inviare messaggi
    // di tipo JSON su una coda.
    // Se invece vuoi inviare solo messaggi di tipo Stringhe allora non c'è bisogno di
    // questo metodo.
    // Il metodo qui sotto verà utilizzato da rabbitTemplate (automaticamente quando verrà eseguito rabbitTemplate.convertAndSend(..) in RabbitMQSender.java)
    // per convertire un oggetto Java in un oggetto JSON e viceversa.
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    // Anche questo metodo qui sotto è necessario quando vogliamo inviare messaggi
    // di tipo JSON su una coda.
    // Se invece vuoi inviare solo messaggi di tipo Stringhe allora non c'è bisogno di
    // questo metodo.
    // Il metodo qui sotto verà invocato da rabbitTemplate (automaticamente quando verrà eseguito rabbitTemplate.convertAndSend(..)
    // in RabbitMQSender.java) e a sua volta al suo interno, il metodo qui sotto invocherà il metodo di sopra converter().
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter()); // setta la conversione da fare
        return rabbitTemplate;
    }
}
