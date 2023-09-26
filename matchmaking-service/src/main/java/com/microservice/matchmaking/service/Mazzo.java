package com.microservice.matchmaking.service;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

@Service
public class Mazzo {

    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @Column(name="nome")
    private String nomemazzo;

    @Column(name="carta_associata")
    private String cartaassociata;

    @Column(name = "nickname")
    private String nickname;

    @Column(name="popolarita")
    private float popolarita;


    public Mazzo() {
    }

    public Mazzo(String nomemazzo, String cartaassociata, String nickname, float popolarita) {
        this.nomemazzo = nomemazzo;
        this.cartaassociata = cartaassociata;
        this.nickname = nickname;
        this.popolarita=popolarita;
    }


    public float getPopolarita() {
        return popolarita;
    }

    public void setPopolarita(float popolarita) {
        this.popolarita = popolarita;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNomemazzo() {
        return nomemazzo;
    }

    public void setNomemazzo(String nomemazzo) {
        this.nomemazzo = nomemazzo;
    }

    public String getCartaassociata() {
        return cartaassociata;
    }

    public void setCartaassociata(String cartaassociata) {
        this.cartaassociata = cartaassociata;
    }
}
