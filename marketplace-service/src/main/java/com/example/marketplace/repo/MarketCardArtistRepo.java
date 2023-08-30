package com.example.marketplace.repo;

import com.example.marketplace.model.Artista;
import com.example.marketplace.model.CarteInVenditaArtista;
import com.example.marketplace.model.CarteInVenditaTrack;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MarketCardArtistRepo extends CrudRepository<CarteInVenditaArtista,Long> {
    List<CarteInVenditaArtista> findByNome(String nome);
    List<CarteInVenditaArtista> findByPopolaritaGreaterThanEqual(int pop);
    List<CarteInVenditaArtista> findByGenere(String genere);



}
