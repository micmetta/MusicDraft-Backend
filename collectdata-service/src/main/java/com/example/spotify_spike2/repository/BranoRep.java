package com.example.spotify_spike2.repository;

import com.example.spotify_spike2.model.Brano;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BranoRep extends CrudRepository<Brano,Long> {
    List<Brano> findByNome(String nome_brano);
}
