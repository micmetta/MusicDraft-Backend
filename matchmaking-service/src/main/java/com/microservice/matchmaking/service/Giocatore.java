package com.microservice.matchmaking.service;
import lombok.Data;

@Data
public class Giocatore {

    private String nicknameGiocatore;
    private String nomemazzoGiocatore;
    private float  popolaritaMazzoGiocatore;

    public Giocatore(){}

    public Giocatore(String nicknameGiocatore, String nomemazzoGiocatore, float popolaritaMazzoGiocatore) {
        this.nicknameGiocatore = nicknameGiocatore;
        this.nomemazzoGiocatore = nomemazzoGiocatore;
        this.popolaritaMazzoGiocatore = popolaritaMazzoGiocatore;
    }

    public String getNicknameGiocatore() {
        return nicknameGiocatore;
    }

    public void setNicknameGiocatore(String nicknameGiocatore) {
        this.nicknameGiocatore = nicknameGiocatore;
    }

    public String getNomemazzoGiocatore() {
        return nomemazzoGiocatore;
    }

    public void setNomemazzoGiocatore(String nomemazzoGiocatore) {
        this.nomemazzoGiocatore = nomemazzoGiocatore;
    }

    public float getPopolaritaMazzoGiocatore() {
        return popolaritaMazzoGiocatore;
    }

    public void setPopolaritaMazzoGiocatore(float popolaritaMazzoGiocatore) {
        this.popolaritaMazzoGiocatore = popolaritaMazzoGiocatore;
    }
}
