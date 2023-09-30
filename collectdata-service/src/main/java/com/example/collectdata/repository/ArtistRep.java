package com.example.collectdata.repository;
import com.example.collectdata.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRep extends CrudRepository<Artista, String> {

    List<Artista> findByNome(String nome);
    Optional<Artista> findById(String id);
}
