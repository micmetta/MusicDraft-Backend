package com.example.carte_e_mazzi.model;

import jakarta.persistence.*;

@Entity
@Table(name="carte_artista_associate")
public class CarteArtistiN {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_Carta")
    private long id;


    @Column(name="nome")
    private String nome;

    @Column(name="popolarita")
    private int popolarita;

    @Column(name="genere")
    private String genere;

    @Column(name="immagine")
    private String immagine;

    @Column(name="nickname")
    private String nickname;

    public CarteArtistiN() {
    }

    public CarteArtistiN(String nome, int popolarita, String genere, String immagine, String nickname) {
        this.nome = nome;
        this.popolarita = popolarita;
        this.genere = genere;
        this.immagine = immagine;
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPopolarita() {
        return popolarita;
    }

    public void setPopolarita(int popolarita) {
        this.popolarita = popolarita;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
