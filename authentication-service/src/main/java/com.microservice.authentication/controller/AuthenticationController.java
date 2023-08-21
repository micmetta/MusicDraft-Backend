package com.microservice.authentication.controller;


import com.microservice.authentication.model.User;
import com.microservice.authentication.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:8082") //
public class AuthenticationController {

    @Autowired
    AuthenticationRepository repository;

    @PostMapping(value = "/registration")
    public String postUser(@RequestBody User user){

        // Qui dentro ci puoi mettere il servizio iniziale che prende ad esempio i dati in formato json dal frontend in cui ci sono queste due info
        // che l'utente ha inserito nel form di "Registrazione" NEL QUALE E' ARRIVATO DOPO AVER CLICCATO SUL BOTTONE "Crea account"
        // della schermata INIZIALE di "Login" del frontend:
        // - nome_utente
        // - email
        // - password
        List<User> _user = repository.findByNickname(user.getNickname()); // mi serve per sapere se il nickname è già presente nel DB
        String out = ""; // stringa di uscita

        for (User user_c : _user) {
            System.out.println("Name: " + user_c.getNickname() + ", Email: " + user_c.getEmail());
        }
        System.out.println();


        // Prima di aggiungere il nuovo utente al DB controllo se il nickname inserito è già stato utilizzato:
        if(_user.isEmpty()){
            _user = repository.findByEmail(user.getEmail()); // ora controllo se l'email inserita è univoca.
            if(_user.isEmpty()){
                repository.save(new User(user.getNickname(), user.getEmail(), user.getPassword()));
                out = "utente registrato";
            }
            else{
                out = "email non univoca";
            }
        }
        else{
            out = "nickname non univoco";
        }

        return out;
    }

    @GetMapping("/loginGoogle") // invece, chi si collegherà a questo servizio, prima farà il login con google e dopo che sarà loggato vedrà la scritta definita qui sotto.
    public String after_login(){

        // Qui sotto ci puoi mettere invece il servizio che si attiva dopo che l'utente ha premuto sul bottone "Continua con Google" nella schermata INIZIALE di "Login" del frontend.
        // Esso permetterà di far scegliere all'utente uno dei suoi account e restituirà queste info al frontend sottoforma di json:
        // - nome_utente
        // - email
        // NEL FRONTEND, una volta ottenuto il risultato e quindi riempiti i campi principali, COMUNQUE POI L'UTENTE DOVRA' scegliere la sua password !!!!!

        DefaultOidcUser info_user = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email_user = info_user.getEmail();
        String name_user = info_user.getFullName();
        String str1 = "Complimenti, " + name_user;
        String str2 = "La tua email è: " + email_user;
        String out = str1 + "<br>" + str2; //<br> serve per far capire al broswer (che interpreta l'html) che deve andare a capo.

        System.out.println("Scritta solo per il commit nel master");

        System.out.println("Email utente: " + email_user);
        System.out.println("Nome utente: " + name_user);
        System.out.println("out: " + out);

        return out; //resituisco al broswer (al frontend) la stringa out che verrà visualizzata.
    }


    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){ //DEVI METTERE @RequestBody passandogli il Nickname e la Password... (DA FARE...)

        List<User> allUsers = new ArrayList<>();
        Iterable<User> users = repository.findAll();
        for(User user : users){
            allUsers.add(user);
        }

        return allUsers;
    }


    //aggiungi il servizio REST /login...
    // Questo microservizio permetterà agli utenti giè registrati di poter loggare nella web app
    @GetMapping("/loginRegistered/{nickname}/{password}")
    public String checkNicknameAndPassword(@PathVariable String nickname, @PathVariable String password){ //DEVI METTERE @RequestBody passandogli il Nickname e la Password... (DA FARE...)

        // Qui devi usare repository.findByNickname e repository.findByPassword,
        // se l'utente esiste nel db_utenti allora puoi restituire per adesso una stringa di output ad esempio.
        // Nell'applicazione finale, quando un utente si logga verrà mandato un piccolo messaggio di conferma al FRONTEND in modo tale che
        // possa far apparire la schermata principale dell'utente che si è loggato.
        String type_login = "non registrato"; // mi permette di sapere se l'utente quando ha fatto il login ha inserito il nickname o l'email
        User _user = repository.findByNicknameAndPassword(nickname, password); // controllo se è loggato con il nickname

        if(_user != null){
            type_login = "nickname" ;
        }
        else{
            _user = repository.findByEmailAndPassword(nickname, password); // controllo se è loggato con l'email
            if(_user != null){
                type_login = "email" ;
            }
        }

        //return _user != null;
        return type_login;
    }

    @GetMapping("/findByNickname/{nickname}")
    public String existNickname(@PathVariable String nickname){

        System.out.println("Sono in nel metodo di AuthenticationController e sto per invocare repository.findByNickname(nickname).");
        List<User> _user = repository.findByNickname(nickname);
        System.out.println("Sono in nel metodo di AuthenticationController e sto per restituire la risposta al microservizio chiamante.");

        if(!_user.isEmpty()) {
            return "nickname esistente";
        }
        else {
            return "nickname inesistente";
        }
    }


}
