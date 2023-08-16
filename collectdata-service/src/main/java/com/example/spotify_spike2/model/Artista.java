package com.example.spotify_spike2.model;


import jakarta.persistence.*;

@Entity
@Table(name="artista")

public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="nome")
    private String nome;

    @Column(name="popolarita")
    private int popolarita;

    @Column(name="genere")
    private String genere;

    @Column(name="immagine")
    private String immagine;

    public Artista() {
    }

    public Artista(String nome, int popolarita, String genere,String immagine) {
        this.nome = nome;
        this.popolarita = popolarita;
        this.genere = genere;
        this.immagine=immagine;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
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

    public int getPopolarità() {
        return popolarita;
    }

    public void setPopolarità(int popolarità) {
        this.popolarita = popolarita;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}
