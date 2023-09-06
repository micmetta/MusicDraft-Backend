package com.example.carte_e_mazzi.repo;

import com.example.carte_e_mazzi.model.CarteBraniN;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UtenteRepoCarteBrano extends CrudRepository<CarteBraniN,Long> {
    List<CarteBraniN> findByNickname (String nick);
    List<CarteBraniN> findById(String id);
}
