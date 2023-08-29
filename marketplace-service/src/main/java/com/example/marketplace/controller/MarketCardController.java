package com.example.marketplace.controller;

import com.example.marketplace.model.Artista;
import com.example.marketplace.model.Brano;
import com.example.marketplace.model.CarteInVenditaArtista;
import com.example.marketplace.model.CarteInVenditaTrack;
import com.example.marketplace.repo.MarketCardArtistRepo;
import com.example.marketplace.repo.MarketCardTrackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/marketplace")
public class MarketCardController {
    @Autowired
    MarketCardArtistRepo repositoryA;

    @Autowired
    MarketCardTrackRepo repositoryT;

    @GetMapping("/creaCardArtista")
    public void crea_Card_artista() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Artista[]> responseEntity = restTemplate.getForEntity("http://collectdata-service:9090/api/v1/show-artist", Artista[].class);
        Artista[] artistArray = responseEntity.getBody();

        if (artistArray != null) {
            for (Artista items : artistArray) {

                CarteInVenditaArtista _artista = repositoryA.save(new CarteInVenditaArtista(items.getNome(), items.getPopolarita(), items.getGenere(), items.getImmagine()));
            }
        }
    }

    @GetMapping("/creaCardTrack")
    public void crea_Card_Brano() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Brano[]> responseEntity = restTemplate.getForEntity("http://collectdata-service:9090/api/v1/show-track", Brano[].class);
        Brano[] trackArray = responseEntity.getBody();

        if (trackArray != null) {
            for (Brano items : trackArray) {

                CarteInVenditaTrack _brano = repositoryT.save(new CarteInVenditaTrack(items.getNome(), items.getDurata(), items.getAnno_pubblicazione(), items.getPopolarita(), items.getImmagine()));
            }
        }
    }

    @GetMapping("/cercaCarta/{tipo}/{metodo}/{parametro}")

    public List<?> cerca_Carta(@PathVariable String tipo, @PathVariable String metodo, @PathVariable String parametro) {
        List<?> c = new ArrayList<>();

        if ("Artista".equals(tipo)) {
            if ("nome".equals(metodo)) {
                c = repositoryA.findByNome(parametro);
            } else if ("popolarita".equals(metodo)) {
                c = repositoryA.findByPopolaritaGreaterThanEqual(Integer.parseInt(parametro));
            } else if ("genere".equals(metodo)) {
                c = repositoryA.findByGenere(parametro);
            }
        } else if ("Brano".equals(tipo)) {
            if ("nome".equals(metodo)) {
                c = repositoryT.findByNome(parametro);
            } else if ("popolarita".equals(metodo)) {
                c = repositoryT.findByPopolaritaGreaterThanEqual(Integer.parseInt(parametro));
            }
        }
        System.out.println(c);
        return c;
    }

    @GetMapping("/show-carteinvenditaArtista")
    public List<CarteInVenditaArtista> showcarteArtista() {

        List<CarteInVenditaArtista> carteA = new ArrayList<>();
        repositoryA.findAll().forEach(carteA::add);

        return carteA;
    }

    @GetMapping("/show-carteinvenditaBrano")
    public List<CarteInVenditaTrack> showcarteBrano() {

        List<CarteInVenditaTrack> carteT = new ArrayList<>();
        repositoryT.findAll().forEach(carteT::add);

        return carteT;
    }

    @DeleteMapping("/delete-CardArtist/{nome_artista}")
    public void delete_artist(@PathVariable String nome_artista) {
        System.out.println("sto qua");
        List<CarteInVenditaArtista> artista = repositoryA.findByNome(nome_artista);
        if (artista != null) {
            for (CarteInVenditaArtista a : artista) {
                repositoryA.delete(a);
                System.out.println("Entity " + nome_artista + " deleted successfully");
            }
        } else {
            System.out.println("Entity " + nome_artista + " not found");
        }

    }

    @DeleteMapping("/delete-CardBrano/{nome_brano}")
    public void delete_track(@PathVariable String nome_brano) {
        System.out.println("sto qua");
        List<CarteInVenditaTrack> track = repositoryT.findByNome(nome_brano);
        if (nome_brano != null) {
            for (CarteInVenditaTrack a : track) {
                repositoryT.delete(a);
                System.out.println("Entity " + nome_brano + " deleted successfully");
            }
        } else {
            System.out.println("Entity " + nome_brano + " not found");
        }

    }
}


