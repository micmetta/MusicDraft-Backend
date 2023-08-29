package com.example.collectdata.controller;

import com.example.collectdata.repository.BranoRep;
import com.example.collectdata.model.Auth;
import com.example.collectdata.model.Brano;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TrackController {
    @Autowired
    BranoRep repository;
    Auth user = new Auth();

    @PostMapping("/insert-track")
    public  void insertBrano_Sync(@RequestBody String nome_brano) {


        user.clientCredentials_Sync();
        SpotifyApi spotifyapi = user.getSpotifyApi();
        final SearchTracksRequest searchAlbumsRequest = spotifyapi.searchTracks(nome_brano)

                .limit(50)
                .offset(0)
                .includeExternal("audio")
                .build();

        try {


            final Paging<Track> albumSimplifiedPaging = searchAlbumsRequest.execute();
            Track[] items = albumSimplifiedPaging.getItems();
            for(int i = 0; i< items.length;i++) {
                String durata="";
                long minuti = (items[i].getDurationMs()/1000)/60;
                long secondi = (items[i].getDurationMs()/1000)%60;
                durata+=minuti+"minuti e "+secondi+"secondi";

                if(items[i].getAlbum().getImages().length !=0) {
                    Brano _brano = repository.save(new Brano(items[i].getName(), durata, items[i].getAlbum().getReleaseDate(), items[i].getPopularity().intValue(), items[i].getAlbum().getImages()[0].getUrl()));
                }else{
                    Brano _brano = repository.save(new Brano(items[i].getName(), durata, items[i].getAlbum().getReleaseDate(), items[i].getPopularity(), "https://www.heartoftheorient.com/wp-content/uploads/2018/08/utente-sconosciuto.png"));

                }
            }


        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());

        }
    }
    @GetMapping("/show-track")
    public List<Brano> showBrano_Sync() {
        System.out.println("Get All customer");
        List<Brano> brani = new ArrayList<>();
        repository.findAll().forEach(brani::add);
        return brani;
    }

    @DeleteMapping("/delete-track/{nome_brano}")
    public  void delete_track(@PathVariable  String nome_brano){
        System.out.println("sto qua");

        List<Brano> brano = repository.findByNome(nome_brano);
        if (brano != null) {
            for(Brano b: brano) {
                repository.delete(b);
                System.out.println("Entity " + nome_brano + " deleted successfully");
            }
        } else {
                System.out.println( "Entity " + nome_brano + " not found");
        }

    }
}
