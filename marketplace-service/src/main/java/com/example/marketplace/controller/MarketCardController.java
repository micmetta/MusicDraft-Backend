package com.example.marketplace.controller;

import com.example.marketplace.model.Artista;
import com.example.marketplace.model.Brano;
import com.example.marketplace.model.CarteInVenditaArtista;
import com.example.marketplace.model.CarteInVenditaTrack;
import com.example.marketplace.repo.MarketCardArtistRepo;
import com.example.marketplace.repo.MarketCardTrackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        ResponseEntity<Artista[]> responseEntity = restTemplate.getForEntity("http://collectdata-service:9090/api/v1/collect/show-artist", Artista[].class);
        Artista[] artistArray = responseEntity.getBody();

        if (artistArray != null) {
            for (Artista items : artistArray) {

                CarteInVenditaArtista _artista = repositoryA.save(new CarteInVenditaArtista(items.getId(),items.getNome(), items.getPopolarita(), items.getGenere(), items.getImmagine()));
            }
        }
    }

    @GetMapping("/creaCardTrack")
    public void crea_Card_Brano() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Brano[]> responseEntity = restTemplate.getForEntity("http://collectdata-service:9090/api/v1/collect/show-track", Brano[].class);
        Brano[] trackArray = responseEntity.getBody();

        if (trackArray != null) {
            for (Brano items : trackArray) {

                CarteInVenditaTrack _brano = repositoryT.save(new CarteInVenditaTrack(items.getId(), items.getNome(), items.getDurata(), items.getAnno_pubblicazione(), items.getPopolarita(), items.getImmagine()));
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

    @DeleteMapping("/delete-CardArtist/{id}")
    public void delete_artist(@PathVariable String id) {
        System.out.println("Deleting artist with ID: " + id);

        List<CarteInVenditaArtista> artista = repositoryA.findById(id);

        if (artista != null) {
            for (CarteInVenditaArtista a: artista) {
                repositoryA.delete(a);
                System.out.println("Artist with ID " + id + " deleted successfully");
            }
            } else{
                System.out.println("Artist with ID " + id + " not found");
            }


    }

    @DeleteMapping("/delete-CardBrano/{id}")
    public void delete_track(@PathVariable String id) {
        System.out.println("Deleting artist with ID: " + id);

        List<CarteInVenditaTrack> track = repositoryT.findById(id);

        if (track != null) {
            for(CarteInVenditaTrack t:track) {
                repositoryT.delete(t);
                System.out.println("Artist with ID " + id + " deleted successfully");
            }
        } else {
            System.out.println("Artist with ID " + id + " not found");
        }

    }
    @PostMapping("/vendiCartaArtista/{nickname}")
    public ResponseEntity<?> insert_cart_Artist(@PathVariable String nickname,@RequestBody CarteInVenditaArtista data){
        System.out.println(nickname);
        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<Integer> pointUtente = restTemplate1.getForEntity("http://authentication-service:8081/api/v1/authenticationService/getPoints/" + data.getNick(), Integer.class);
        if(pointUtente.getBody() < data.getCosto()*20 ) {
            CarteInVenditaArtista _artista = repositoryA.save(new CarteInVenditaArtista(data.getId(), data.getNome(), data.getPopolarita(), data.getGenere(), data.getImmagine(),data.getCosto(), nickname));
            return ResponseEntity.ok(_artista);
        }else{
            String errorMessage = "Punti insufficienti per acquistare questa carta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }
    @PostMapping("/vendiCartaBrano/{nickname}")
    public ResponseEntity<?> insert_cart_Artist(@PathVariable String nickname,@RequestBody CarteInVenditaTrack data) {
        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<Integer> pointUtente = restTemplate1.getForEntity("http://authentication-service:8081/api/v1/authenticationService/getPoints/" + data.getNick(), Integer.class);
        if (pointUtente.getBody() < data.getCosto() * 20) {
            CarteInVenditaTrack _artista = repositoryT.save(new CarteInVenditaTrack(data.getId(), data.getNome(), data.getDurata(), data.getAnno_pubblicazione(), data.getPopolarita(), data.getImmagine(), data.getCosto(), nickname));
            return ResponseEntity.ok(_artista);
        } else {
            String errorMessage = "Punti insufficienti per acquistare questa carta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }
    }



