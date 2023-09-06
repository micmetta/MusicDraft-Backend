package com.example.marketplace.model;


//import com.example.marketplace.model.CarteInVenditaArtista;
import jakarta.persistence.*;



public class Artista {


    private String id;


    private String nome;


    private int popolarita;


    private String genere;


    private String immagine;

 //   @OneToOne(mappedBy = "Artista")
  //  private CarteInVenditaArtista card;

    public Artista() {
    }

    public Artista(String id,String nome, int popolarita, String genere, String immagine) {
        this.id=id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
