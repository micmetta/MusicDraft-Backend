package com.example.spotify_spike2.controller;

import com.example.spotify_spike2.model.Artista;
import com.example.spotify_spike2.repository.ArtistRep;
import com.example.spotify_spike2.model.Auth;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArtistController {

    @Autowired
    ArtistRep repository;
    Auth user = new Auth();




    @PostMapping("/insert-artist")
    public  void insertArtista_Sync(@RequestBody  String nome_artista) {


        user.clientCredentials_Sync();
        SpotifyApi spotifyapi = user.getSpotifyApi();
        final SearchArtistsRequest searchAlbumsRequest = spotifyapi.searchArtists(nome_artista)

                .limit(50)
                .offset(0)
                .includeExternal("audio")
                .build();

        try {


            final Paging<Artist> albumSimplifiedPaging = searchAlbumsRequest.execute();
            Artist[] items = albumSimplifiedPaging.getItems();
            for(int i = 0; i< items.length;i++) {
                String genere="";
                if(items[i].getGenres().length == 0){
                    genere="Non disponibile";
                }
                for(int j = 0;j<items[i].getGenres().length;j++) {

                    genere += (items[i].getGenres()[j] + ",");
                }
                System.out.println(i+". nome: "+items[i].getName()+", popolarità:"+items[i].getPopularity()+", genere:"+genere);
                if(items[i].getImages().length!=0) {
                    Artista _artista = repository.save(new Artista(items[i].getName(), items[i].getPopularity().intValue(), genere, items[i].getImages()[0].getUrl()));
                }else{
                    Artista _artista = repository.save(new Artista(items[i].getName(), items[i].getPopularity().intValue(), genere, "https://www.heartoftheorient.com/wp-content/uploads/2018/08/utente-sconosciuto.png"));

                }
            }


        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());

        }
    }


    @GetMapping("/show-artist")
    public  List<Artista> showArtista_Sync() {
        System.out.println("Get All customer");
        List<Artista> artisti = new ArrayList<>();
        repository.findAll().forEach(artisti::add);
        return artisti;
    }


    @DeleteMapping("/delete-artist/{nome_artista}")
    public  void delete_artist(@PathVariable  String nome_artista){
        System.out.println("sto qua");
        List<Artista> artista = repository.findByNome(nome_artista);
        if (artista != null) {
            for (Artista a:artista) {
                repository.delete(a);
                System.out.println("Entity " + nome_artista + " deleted successfully");
            }
        } else {
                System.out.println( "Entity " + nome_artista + " not found");
        }

    }


}




