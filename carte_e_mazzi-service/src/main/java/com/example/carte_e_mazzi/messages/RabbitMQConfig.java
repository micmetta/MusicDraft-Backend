package com.example.carte_e_mazzi.messages;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

//    @Value("${rabbitmq.queue.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String queue;
    @Value("${rabbitmq.queue.updateCards.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
    private String queue_update_cards;


//    @Value("${rabbitmq.exchange.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String exchange;
    @Value("${rabbitmq.exchange.update.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
    private String exchange;


//    @Value("${rabbitmq.routing.key}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String routing_key;
    @Value("${rabbitmq.routing.updateCards.key}")
    private String routingupdateCardsKey;



    // spring bean for rabbitmq queue
//    @Bean
//    public Queue queue(){
//        return new Queue(queue); // setto il nome della coda
//    }
    @Bean
    public Queue updateCardsQueue() {
        return new Queue(queue_update_cards);
    }




    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }


    // Con il metodo qui sotto possiamo collegare tra loro
    // la coda definitia sopra con l'exchange definito sopra
    // attraverso uan certa routingKey che in questo caso è rappresentata
    // dalla stringa "javaguides_routing_key" salvata nella variabile routing_key.
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(queue())
//                .to(exchange())
//                .with(routing_key);
//    }
    @Bean
    public Binding updateCardsBinding(){
        return BindingBuilder
                .bind(updateCardsQueue())
                .to(exchange())
                .with(routingupdateCardsKey);
    }


}
