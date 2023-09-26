package com.microservice.matchmaking.model;

import jakarta.persistence.*;

@Entity //specifico che la classe di sotto è una Entity e quindi avrà una propria tabella con il nome qui sotto.
@Table(name = "matchmaking")
public class Matchmaking {


    @Id //Con questa annotation specifico che ogni riga nella tabella "matchmaking" avrà un id univoco
    @GeneratedValue(strategy = GenerationType.AUTO) //con questa annotation specifico che ogni volta che aggiungo una nuova riga ovvero un nuovo customer, l'id si autoincrementerà da solo.
    private long id;

    @Column(name="nicknameU1")
    private String nicknameU1;

    @Column(name="nicknameU2")
    private String nicknameU2;

    @Column(name="nomemazzoU1")
    private String nomemazzoU1;

    @Column(name="nomemazzoU2")
    private String nomemazzoU2;

    @Column(name="popolaritaMazzoU1")
    private float popolaritaMazzoU1;

    @Column(name="popolaritaMazzoU2")
    private float popolaritaMazzoU2;


    public Matchmaking(){

    }


    public Matchmaking(String nicknameU1, String nicknameU2, String nomemazzoU1, String nomemazzoU2, float popolaritaMazzoU1, float popolaritaMazzoU2) {
        this.nicknameU1 = nicknameU1;
        this.nicknameU2 = nicknameU2;
        this.nomemazzoU1 = nomemazzoU1;
        this.nomemazzoU2 = nomemazzoU2;
        this.popolaritaMazzoU1 = popolaritaMazzoU1;
        this.popolaritaMazzoU2 = popolaritaMazzoU2;
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
