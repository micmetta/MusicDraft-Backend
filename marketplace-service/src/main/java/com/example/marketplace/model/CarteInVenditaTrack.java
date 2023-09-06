package com.example.marketplace.model;


import jakarta.persistence.*;

@Entity
@Table(name="carte_brano")
public class CarteInVenditaTrack {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id_Carta_VB")
        private long id;



    @Column(name = "nome")
    private String nome;

    @Column(name="durata")
    private String durata;

    @Column(name="anno_pubblicazione")
    private String anno_pubblicazione;

    @Column(name="popolarita")
    private int popolarita;

    @Column(name="immagine")
    private String immagine;

    @Column(name="costo")
    private int costo;

    @Column(name="nickname")
    private String nick;

    public CarteInVenditaTrack() {
    }

    public CarteInVenditaTrack(String nome, String durata, String anno_pubblicazione, int popolarita, String immagine) {
        this.nome = nome;
        this.durata = durata;
        this.anno_pubblicazione = anno_pubblicazione;
        this.popolarita = popolarita;
        this.immagine = immagine;
        this.costo = popolarita*10;
        this.nick = "admin";
    }
    public CarteInVenditaTrack(String nome, String durata, String anno_pubblicazione, int popolarita, String immagine,int costo, String nick) {
        this.nome = nome;
        this.durata = durata;
        this.anno_pubblicazione = anno_pubblicazione;
        this.popolarita = popolarita;
        this.immagine = immagine;
        this.costo = costo;
        this.nick=nick;
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

    public long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getAnno_pubblicazione() {
        return anno_pubblicazione;
    }

    public void setAnno_pubblicazione(String anno_pubblicazione) {
        this.anno_pubblicazione = anno_pubblicazione;
    }

    public int getPopolarita() {
        return popolarita;
    }

    public void setPopolarita(int popolarita) {
        this.popolarita = popolarita;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
}

