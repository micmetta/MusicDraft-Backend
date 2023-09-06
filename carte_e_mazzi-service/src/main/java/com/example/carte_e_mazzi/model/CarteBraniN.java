package com.example.carte_e_mazzi.model;

import jakarta.persistence.*;

@Entity
@Table(name="carte_brani_associate")
public class CarteBraniN {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_Carta")
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

    @Column(name="nickname")
    private String nickname;

    public CarteBraniN() {
    }

    public CarteBraniN(String nome, String durata, String anno_pubblicazione, int popolarita, String immagine, String nickname) {
        this.nome = nome;
        this.durata = durata;
        this.anno_pubblicazione = anno_pubblicazione;
        this.popolarita = popolarita;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
