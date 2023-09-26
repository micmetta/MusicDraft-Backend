package com.example.collectdata.model;


//import com.example.marketplace.model.CarteInVenditaArtista;
import jakarta.persistence.*;

@Entity
@Table(name="artista")

public class Artista {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="nome")
    private String nome;

    @Column(name="popolarita")
    private int popolarita;

    @Column(name="genere")
    private String genere;

    @Column(name="immagine")
    private String immagine;

 //   @OneToOne(mappedBy = "Artista")
  //  private CarteInVenditaArtista card;

    public Artista() {
    }

    public Artista(String id,String nome, int popolarita, String genere,String immagine) {
        this.id=id;
        this.nome = nome;
        this.popolarita = popolarita;
        this.genere = genere;
        this.immagine=immagine;
    }

    public void setId(String id) {
        this.id = id;
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
