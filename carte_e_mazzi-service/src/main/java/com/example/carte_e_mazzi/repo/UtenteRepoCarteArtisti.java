package com.example.carte_e_mazzi.repo;

import com.example.carte_e_mazzi.model.CarteArtistiN;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UtenteRepoCarteArtisti extends CrudRepository<CarteArtistiN,Long> {
    List<CarteArtistiN> findByNickname (String nick);
    List<CarteArtistiN> findById(String id);
}
