package com.microservice.home.service;
import com.microservice.home.messages.RabbitMQSender;
import lombok.Data;

@Data
public class GestioneScambiService {

    private long id;
    private String nicknameU1;
    private String nicknameU2;
    private String idCartaRichiesta;
    private String listaCarteOfferte;
    private int pointsOfferti;

    private final RabbitMQSender sender; // inietto l'oggetto di tipo RabbitMQSender all'interno del servizio REST ScambiController che in


    public GestioneScambiService(RabbitMQSender sender){
        this.sender = sender;
    }

    public GestioneScambiService(long id, String nicknameU1, String nicknameU2, String idCartaRichiesta, String listaCarteOfferte, int pointsOfferti, RabbitMQSender sender) {
        this.id = id;
        this.nicknameU1 = nicknameU1;
        this.nicknameU2 = nicknameU2;
        this.idCartaRichiesta = idCartaRichiesta;
        this.listaCarteOfferte = listaCarteOfferte;
        this.pointsOfferti = pointsOfferti;
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNicknameU1() {
        return nicknameU1;
    }

    public void setNicknameU1(String nicknameU1) {
        this.nicknameU1 = nicknameU1;
    }

    public String getNicknameU2() {
        return nicknameU2;
    }

    public void setNicknameU2(String nicknameU2) {
        this.nicknameU2 = nicknameU2;
    }

    public String getIdCartaRichiesta() {
        return idCartaRichiesta;
    }

    public void setIdCartaRichiesta(String idCartaRichiesta) {
        this.idCartaRichiesta = idCartaRichiesta;
    }

    public String getListaCarteOfferte() {
        return listaCarteOfferte;
    }

    public void setListaCarteOfferte(String listaCarteOfferte) {
        this.listaCarteOfferte = listaCarteOfferte;
    }

    public int getPointsOfferti() { return pointsOfferti; }

    public void setPointsOfferti(int pointsOfferti) { this.pointsOfferti = pointsOfferti; }

    public RabbitMQSender getSender() { return sender; }

}

