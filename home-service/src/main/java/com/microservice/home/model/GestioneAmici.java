package com.microservice.home.model;
import jakarta.persistence.*;


@Entity
@Table(name = "gestione_amici")
public class GestioneAmici {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nicknameU1")//specifico che name sarà una colonna della tabella gestione_amici (utente che ha mandato la richiesta di amicizia).
    private String nicknameU1;

    @Column(name = "nicknameU2")//specifico che name sarà una colonna della tabella gestione_amici (utente che ha ricevuto la richiesta di amicizia).
    private String nicknameU2;

    @Column(name = "stato")
    private String stato; // "in attesa" oppure "accettato"

    public GestioneAmici(){}

    public GestioneAmici(String nicknameU1, String nicknameU2, String stato){
        this.nicknameU1 = nicknameU1;
        this.nicknameU2 = nicknameU2;
        this.stato = stato;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNicknameU1() {
        return nicknameU1;
    }

    public void setNicknameU1(String nicknameU1) {
        this.nicknameU1 = nicknameU1;
    }

    public String getNicknameU2() {
        return nicknameU2;
    }

    public void setNicknameU2(String nicknameU2) {
        this.nicknameU2 = nicknameU2;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
