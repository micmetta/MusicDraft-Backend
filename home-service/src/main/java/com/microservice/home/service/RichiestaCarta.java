package com.microservice.home.service;
import java.util.List;

public class RichiestaCarta {
    private String nicknameU1;
    private String nicknameU2;
    private String idCartaRichiesta;
    private List<String> listaCarteOfferte;
    private int pointsOfferti;
    private String statoOfferta;

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

    public List<String> getListaCarteOfferte() {
        return listaCarteOfferte;
    }

    public void setListaCarteOfferte(List<String> listaCarteOfferte) {
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
}
