package com.microservice.authentication.messages;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

//    @Value("${rabbitmq.queue.updatePoints.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String queue;
    @Value("${rabbitmq.queue.updatePoints.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
    private String queue_update_points;


//    @Value("${rabbitmq.exchange.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String exchange;
    @Value("${rabbitmq.exchange.update.name}") // questo valore l'ho settato nell'application proprieties di questo microservizio
    private String exchange;


//    @Value("${rabbitmq.routing.updatePoints.key}") // questo valore l'ho settato nell'application proprieties di questo microservizio
//    private String routing_key;
    @Value("${rabbitmq.routing.updatePoints.key}")
    private String routingUpdatePointsKey;



    // spring bean for rabbitmq queue
//    @Bean
//    public Queue queue(){
//        return new Queue(queue); // setto il nome della coda
//    }

    @Bean
    public Queue updatePointsQueue() {
        return new Queue(queue_update_points);
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
//                .with(routing_key);//
//    }
    @Bean Binding updatePointsBinding(){
        return BindingBuilder
                .bind(updatePointsQueue())
                .to(exchange())
                .with(routingUpdatePointsKey);
    }
}
