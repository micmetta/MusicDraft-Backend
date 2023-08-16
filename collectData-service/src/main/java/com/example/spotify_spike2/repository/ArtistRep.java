package com.example.spotify_spike2.repository;

import com.example.spotify_spike2.model.Artista;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ArtistRep extends CrudRepository<Artista,Long> {
    List<Artista> findByNome(String nome);
}
