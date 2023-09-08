package com.microservice.home.service;
import com.microservice.home.messages.RabbitMQSender;
import lombok.Data;

@Data
public class GestioneScambiService {

    private long id;
    private String nicknameU1;
    private String nicknameU2;
    private String idCartaRichiesta;
    private String tipoCartaRichiesta;
    private String listaCarteOfferte;
    private String listaTipiCarteOfferte;
    private int pointsOfferti;

    private final RabbitMQSender sender; // inietto l'oggetto di tipo RabbitMQSender all'interno del servizio REST ScambiController che in


    public GestioneScambiService(RabbitMQSender sender){
        this.sender = sender;
    }

    public GestioneScambiService(long id, String nicknameU1, String nicknameU2, String idCartaRichiesta, String tipoCartaRichiesta, String listaCarteOfferte,  String listaTipiCarteOfferte, int pointsOfferti, RabbitMQSender sender) {
        this.id = id;
        this.nicknameU1 = nicknameU1;
        this.nicknameU2 = nicknameU2;
        this.idCartaRichiesta = idCartaRichiesta;
        this.tipoCartaRichiesta = tipoCartaRichiesta;
        this.listaCarteOfferte = listaCarteOfferte;
        this.listaTipiCarteOfferte = listaTipiCarteOfferte;
        this.pointsOfferti = pointsOfferti;
        this.sender = sender;
    }

    public RabbitMQSender getSender() { return sender; }

}

