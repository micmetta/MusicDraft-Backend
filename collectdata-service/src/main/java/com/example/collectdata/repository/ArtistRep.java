package com.example.collectdata.repository;

import com.example.collectdata.model.Artista;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ArtistRep extends CrudRepository<Artista,Long> {
    List<Artista> findByNome(String nome);
}
