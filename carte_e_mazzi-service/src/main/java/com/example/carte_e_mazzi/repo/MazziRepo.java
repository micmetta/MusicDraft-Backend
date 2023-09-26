package com.example.carte_e_mazzi.repo;

import com.example.carte_e_mazzi.model.Mazzi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MazziRepo extends CrudRepository<Mazzi,Long> {
    List<Mazzi> findByNomemazzo(String nome_mazzo);
    List<Mazzi> findByNickname(String nickname);
    List<Mazzi> findByCartaassociataAndNickname(String id_carta,String nickname);
}
