package com.example.marketplace.repo;

import com.example.marketplace.model.CarteInVenditaArtista;
import com.example.marketplace.model.CarteInVenditaTrack;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MarketCardTrackRepo extends CrudRepository<CarteInVenditaTrack,Long> {
    List<CarteInVenditaTrack> findById(String id);
    List<CarteInVenditaTrack> findByPopolaritaGreaterThanEqual(int pop);
    List<CarteInVenditaTrack> findByNome(String nome);

}
