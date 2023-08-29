package com.example.marketplace.model;


//import com.example.marketplace.model.CarteInVenditaArtista;
import jakarta.persistence.*;



public class Artista {


    private long id;


    private String nome;


    private int popolarita;


    private String genere;


    private String immagine;

 //   @OneToOne(mappedBy = "Artista")
  //  private CarteInVenditaArtista card;

    public Artista() {
    }

    public Artista(String nome, int popolarita, String genere, String immagine) {
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
}
