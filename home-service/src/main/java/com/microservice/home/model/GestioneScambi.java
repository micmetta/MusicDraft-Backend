package com.microservice.home.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "gestione_scambi")
public class GestioneScambi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nicknameU1") // specifico che name sarà una colonna della tabella gestione_amici (utente che ha mandato l'offerta di scambio).
    private String nicknameU1;

    @Column(name = "nicknameU2")// specifico che name sarà una colonna della tabella gestione_amici (utente che ha ricevuto l'offerta di scambio).
    private String nicknameU2;

    @Column(name = "idCartaRichiesta") // campo che conterrà l'id della carta richiesta da nicknameU1 che è presente nelle carte possedute da nicknameU2.
    private String idCartaRichiesta;

    @Column(name = "listaCarteOfferte", columnDefinition = "JSON") // campo che conterrà la lista delle carte (anche solo una o 0 perchè magari ha offerto solo points)
    // offerte da "nicknameU1" in cambio di "cartaRichiesta".
    // private List<String> listaCarteOfferte;
    private String listaCarteOfferte;

    @Column(name = "pointsOfferti") // conterrà il quantitativo di points offerti (può essere anche 0 perchè magari ha offerto solo
    // delle carte) da "nicknameU1" a "nicknameU2" per la "cartaRichiesta"
    private int pointsOfferti;

    @Column(name = "statoOfferta") // "in attesa", "controfferta"
    private String statoOfferta;

    @Column(name = "idStart") // questo id mi permette di sapere sempre a quale offerta iniziale è legata un'eventuale controfferta.
    private long idStart;

    @Column(name = "numControfferta") // questo numero mi dice qual è il numero corrente della controfferta. Questo sarà == 0 solo all'inizio
    // quando viene proposta la prima offerta.
    private int numControfferta;

    public GestioneScambi(){
    }

    public GestioneScambi(String nicknameU1, String nicknameU2, String idCartaRichiesta, String listaCarteOfferte, int pointsOfferti, String statoOfferta, long idStart, int numControfferta){
        this.nicknameU1 = nicknameU1;
        this.nicknameU2 = nicknameU2;
        this.idCartaRichiesta = idCartaRichiesta;
        this.listaCarteOfferte = listaCarteOfferte;
        this.pointsOfferti = pointsOfferti;
        this.statoOfferta = statoOfferta;
        this.idStart = idStart;
        this.numControfferta = numControfferta;
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

    public String getIdCartaRichiesta() {
        return idCartaRichiesta;
    }

    public void setIdCartaRichiesta(String idCartaRichiesta) {
        this.idCartaRichiesta = idCartaRichiesta;
    }

    public String getListaCarteOfferte() {
        return listaCarteOfferte;
    }

    public void setListaCarteOfferte(String listaCarteOfferte) {
        this.listaCarteOfferte = listaCarteOfferte;
    }

    public int getPointsOfferti() {
        return pointsOfferti;
    }

    public void setPointsOfferti(int pointsOfferti) {
        this.pointsOfferti = pointsOfferti;
    }

    public String getStatoOfferta() {
        return statoOfferta;
    }

    public void setStatoOfferta(String statoOfferta) {
        this.statoOfferta = statoOfferta;
    }

    public long getIdStart() { return idStart; }

    public void setIdStart(long idStart) { this.idStart = idStart; }

    public int getNumControfferta() { return numControfferta; }

    public void setNumControfferta(int numControfferta) { this.numControfferta = numControfferta; }
}
