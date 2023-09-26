package com.microservice.matchmaking.model;

import jakarta.persistence.*;
import java.time.LocalDate;



@Entity
@Table(name = "riepilogoPartitaConclusa")
public class RiepilogoPartitaConclusa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "dataGiocata")
    private LocalDate dataGiocata;

    @Column(name="nickVincitore")
    private String nickVincitore;

    @Column(name="nicknameU1")
    private String nicknameU1;

    @Column(name="nicknameU2")
    private String nicknameU2;

    @Column(name="nomemazzoU1")
    private String nomemazzoU1;

    @Column(name="nomemazzoU2")
    private String nomemazzoU2;

    @Column(name="carteAssociateMazzoU1", columnDefinition = "JSON")
    private String carteAssociateMazzoU1;

    @Column(name="carteAssociateMazzoU2", columnDefinition = "JSON")
    private String carteAssociateMazzoU2;

    @Column(name="popolaritaMazzoU1")
    private float popolaritaMazzoU1;

    @Column(name="popolaritaMazzoU2")
    private float popolaritaMazzoU2;


    public RiepilogoPartitaConclusa(){}

    public RiepilogoPartitaConclusa(LocalDate dataGiocata, String nickVincitore, String nicknameU1, String nicknameU2, String nomemazzoU1, String nomemazzoU2, String carteAssociateMazzoU1, String carteAssociateMazzoU2, float popolaritaMazzoU1, float popolaritaMazzoU2) {
        this.dataGiocata = dataGiocata;
        this.nickVincitore = nickVincitore;
        this.nicknameU1 = nicknameU1;
        this.nicknameU2 = nicknameU2;
        this.nomemazzoU1 = nomemazzoU1;
        this.nomemazzoU2 = nomemazzoU2;
        this.carteAssociateMazzoU1 = carteAssociateMazzoU1;
        this.carteAssociateMazzoU2 = carteAssociateMazzoU2;
        this.popolaritaMazzoU1 = popolaritaMazzoU1;
        this.popolaritaMazzoU2 = popolaritaMazzoU2;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataGiocata() {
        return dataGiocata;
    }

    public void setDataGiocata(LocalDate dataGiocata) {
        this.dataGiocata = dataGiocata;
    }


    public String getNickVincitore() {
        return nickVincitore;
    }

    public void setNickVincitore(String nickVincitore) {
        this.nickVincitore = nickVincitore;
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

    public String getNomemazzoU1() {
        return nomemazzoU1;
    }

    public void setNomemazzoU1(String nomemazzoU1) {
        this.nomemazzoU1 = nomemazzoU1;
    }

    public String getNomemazzoU2() {
        return nomemazzoU2;
    }

    public void setNomemazzoU2(String nomemazzoU2) {
        this.nomemazzoU2 = nomemazzoU2;
    }

    public String getCarteAssociateMazzoU1() {
        return carteAssociateMazzoU1;
    }

    public void setCarteAssociateMazzoU1(String carteAssociateMazzoU1) {
        this.carteAssociateMazzoU1 = carteAssociateMazzoU1;
    }

    public String getCarteAssociateMazzoU2() {
        return carteAssociateMazzoU2;
    }

    public void setCarteAssociateMazzoU2(String carteAssociateMazzoU2) {
        this.carteAssociateMazzoU2 = carteAssociateMazzoU2;
    }

    public float getPopolaritaMazzoU1() {
        return popolaritaMazzoU1;
    }

    public void setPopolaritaMazzoU1(float popolaritaMazzoU1) {
        this.popolaritaMazzoU1 = popolaritaMazzoU1;
    }

    public float getPopolaritaMazzoU2() {
        return popolaritaMazzoU2;
    }

    public void setPopolaritaMazzoU2(float popolaritaMazzoU2) {
        this.popolaritaMazzoU2 = popolaritaMazzoU2;
    }
}
