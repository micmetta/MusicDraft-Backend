package com.example.carte_e_mazzi.model;
import jakarta.persistence.*;


public class CarteInVenditaArtista {


    private long id;



    private String nome;

    private int popolarita;

    private String genere;

    private String immagine;

    public CarteInVenditaArtista() {
    }

    public CarteInVenditaArtista(String nome, int popolarita, String genere, String immagine) {
        this.nome = nome;
        this.popolarita = popolarita;
        this.genere = genere;
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
}
