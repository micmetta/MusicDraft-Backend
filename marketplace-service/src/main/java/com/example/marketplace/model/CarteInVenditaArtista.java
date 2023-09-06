package com.example.marketplace.model;
import jakarta.persistence.*;

@Entity
@Table(name="carta_artista")
public class CarteInVenditaArtista {

    @Id

    @Column(name="id_Carta_VA")
    private String id;


    @Column(name="nome")
    private String nome;

    @Column(name="popolarita")
    private int popolarita;

    @Column(name="genere")
    private String genere;

    @Column(name="immagine")
    private String immagine;

    @Column(name="costo")
    private int costo;

    @Column(name="nickname")
    private String nick;

    public CarteInVenditaArtista() {
    }

    public CarteInVenditaArtista(String id,String nome, int popolarita, String genere, String immagine) {
        this.id=id;
        this.nome = nome;
        this.popolarita = popolarita;
        this.genere = genere;
        this.immagine = immagine;
        this.costo = popolarita*10;
        this.nick= "admin";
    }




    public CarteInVenditaArtista(String id,String nome, int popolarita, String genere, String immagine, int costo, String nick) {
        this.id=id;

        this.nome = nome;
        this.popolarita = popolarita;
        this.genere = genere;
        this.immagine = immagine;
        this.costo = costo;
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
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

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
}
