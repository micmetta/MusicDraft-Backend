package com.microservice.home.model;
import jakarta.persistence.*;


@Entity
@Table(name = "gestione_amici")
public class GestioneAmici {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nickameU1")//specifico che name sarà una colonna della tabella customer.
    private String nickameU1;

    @Column(name = "nicknameU2")//specifico che name sarà una colonna della tabella customer.
    private String nicknameU2;

    @Column(name = "stato")//specifico che name sarà una colonna della tabella customer.
    private String stato; // "in attesa" oppure "accettato"

    public GestioneAmici(){}

    public GestioneAmici(String nickameU1, String nicknameU2, String stato){
        this.nickameU1 = nickameU1;
        this.nicknameU2 = nicknameU2;
        this.stato = stato;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickameU1() {
        return nickameU1;
    }

    public void setNickameU1(String nickameU1) {
        this.nickameU1 = nickameU1;
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
