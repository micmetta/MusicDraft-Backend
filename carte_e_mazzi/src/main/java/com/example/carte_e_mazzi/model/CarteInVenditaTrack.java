package com.example.carte_e_mazzi.model;




import jakarta.persistence.*;


public class CarteInVenditaTrack {


    private String id;




    private String nome;


    private String durata;


    private String anno_pubblicazione;


    private int popolarita;


    private String immagine;


    private int costo;


    private String nick;

    public CarteInVenditaTrack() {
    }

    public CarteInVenditaTrack(String id,String nome, String durata, String anno_pubblicazione, int popolarita, String immagine) {
        this.id=id;
        this.nome = nome;
        this.durata = durata;
        this.anno_pubblicazione = anno_pubblicazione;
        this.popolarita = popolarita;
        this.immagine = immagine;
        this.costo = popolarita*10;
        this.nick = "admin";
    }
    public CarteInVenditaTrack(String id,String nome, String durata, String anno_pubblicazione, int popolarita, String immagine,int costo, String nick) {
        this.id=id;
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

