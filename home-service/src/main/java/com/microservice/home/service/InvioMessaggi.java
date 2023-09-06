package com.microservice.home.service;

import org.springframework.stereotype.Service;

@Service
public class InvioMessaggi {

    public InvioMessaggi(){}

    public void invia_messaggio_aggiornamento_carte(GestioneScambiService obj){
        obj.getSender().sendUpdateCardsMessage(obj);
        System.out.println("SONO DENTRO invia_messaggio_aggiornamento_carte");
    }

    public void invia_messaggio_aggiornamento_points(GestioneScambiService obj){
        obj.getSender().sendUpdatePointsMessage(obj);
//        obj.getSender().sendJsonMessage(obj); funziona
        System.out.println("SONO DENTRO invia_messaggio_aggiornamento_points");
    }

}
