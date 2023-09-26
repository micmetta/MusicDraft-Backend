package com.example.collectdata.model;

//import com.example.marketplace.model.CarteInVenditaTrack;
import jakarta.persistence.*;

@Entity
@Table(name="brani")
public class Brano {

    @Id
    @Column(name="id")
    private String id;

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

   // @OneToOne(mappedBy = "Brano")
    //private CarteInVenditaTrack card;

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getImmagine() {
        return immagine;
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

    public Brano() {
    }

    public Brano(String id,String nome_brano, String durata, String anno_pubblicazione, int popolarita, String immagine) {
        this.id=id;
        this.nome = nome_brano;
        this.durata = durata;
        this.anno_pubblicazione = anno_pubblicazione;
        this.popolarita = popolarita;
        this.immagine = immagine;
    }
}
