package com.microservice.authentication.service;

import com.microservice.authentication.model.User;
import com.microservice.authentication.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateUser {

    private final AuthenticationRepository repository;
    private String nickname;
    private int points;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String aggiornamento_points(String nickname, int points){

        List<User> _user = repository.findByNickname(nickname);
        int points_correnti = 0;

        if(!_user.isEmpty()){
            points_correnti = _user.get(0).getPoints();
            _user.get(0).setPoints(points_correnti + points);
            repository.save(_user.get(0)); // memorizzo l'aggiornamento nel DB

            return "Aggiornamento points completato.";
        }
        else{
            return "Errore: il nickname non esiste nella tabella utenti.";
        }
    }

}
