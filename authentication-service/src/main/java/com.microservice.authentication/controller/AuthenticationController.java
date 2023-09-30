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
@RequestMapping("/api/v1/authenticationService")
//@CrossOrigin(origins = "http://localhost:8082") //
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
                // a questo punto MEMORIZZO UN NUOVO UTENTE nella tabella utenti.
                // inserisco direttamente qui nel backend lo stato isOnline=true.
                // i points INIZIALI LI SETTO QUI NEL BACKEND
                repository.save(new User(user.getNickname(), user.getEmail(), user.getPassword(), true, 1000));
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


    // restituisce tutti gli utenti registrati
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){

        List<User> allUsers = new ArrayList<>();
        Iterable<User> users = repository.findAll();
        for(User user : users){
            allUsers.add(user);
        }

        return allUsers;
    }


    // aggiungi il servizio REST /login...
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

    // - aggiunta dopo che PIETRO HA FATTO FUNZIONARE KUBERNETIS..
    // questo endpoint permette di ottenere il numero totale di utenti che sono online in questo momento:
    @GetMapping("/getNumberUsersOnline")
    public int get_number_users_online(){
        List<User> _users = repository.findAll();
        int num_utenti_online = 0;

        for (User utente: _users) {
            if(utente.getisOnline()){
                num_utenti_online++;
            }
        }

        return num_utenti_online;
    }



    // - aggiunta dopo che PIETRO HA FATTO FUNZIONARE KUBERNETIS..
    // questo endpoint permette ad un utente di poter modificare il proprio nickname:
    @PutMapping("/updateNickname/{nicknameCurrent}/{nicknameNew}")
    public String update_nickname(@PathVariable String nicknameCurrent, @PathVariable String nicknameNew){
        List<User> _user = repository.findByNickname(nicknameCurrent); // in realtà si potrebbe mettere anche solo User invece di List<User>
        if(!_user.isEmpty()){
            // adesso verifico se il nuovo nickname che si vuole settare è già presente nel DB:
            List<User> check_user = repository.findByNickname(nicknameNew);
            if(check_user.isEmpty()){
                // allora il nuovo nickname può essere settato
                _user.get(0).setNickname(nicknameNew);
                repository.save(_user.get(0));

                return "Il nuovo nickname è stato settato correttamente.";
            }
            else{
                return "Il nuovo nickname che si vuole registrare esiste già.";
            }
        }
        else{
            return "nickname corrente inesistente";
        }
    }

    // - aggiunta dopo che PIETRO HA FATTO FUNZIONARE KUBERNETIS..
    // questo endpoint permette ad un utente di poter modificare la propria password:
    @PutMapping("/updatePassword/{nickname}/{passwordNew}")
    public String update_password(@PathVariable String nickname, @PathVariable String passwordNew){
        List<User> _user = repository.findByNickname(nickname); // in realtà si potrebbe mettere anche solo User invece di List<User>
        if(!_user.isEmpty()){
            _user.get(0).setPassword(passwordNew);
            repository.save(_user.get(0));

            return "La nuova password è stata settata correttamente";
        }
        else{
            return "nickname corrente inesistente";
        }
    }




    // questo endpoint mi permette dato un certo nickname in input di ottenere la sua email:
    @GetMapping("/getEmail/{nickname}")
    public String get_Email(@PathVariable String nickname){
        List<User> _user = repository.findByNickname(nickname);
        String email_user = "";
        if(!_user.isEmpty()){
            email_user = _user.get(0).getEmail();

            return email_user;
        }
        else{
            return "nickname inesistente.";
        }
    }

    // questo endpoint per un utente mi permette dato una certa email in input di ottenere il suo nickname:
    @GetMapping("/getNickname/{email}")
    public String get_Nickname(@PathVariable String email){
        List<User> _user = repository.findByEmail(email);
        String nickname_user = "";

        if(!_user.isEmpty()){
            nickname_user = _user.get(0).getNickname();

            return nickname_user;
        }
        else{
            return "email inesistente.";
        }
    }


    // endpoint per sapere se un certo utente è online o no:
    @GetMapping("/getIsOnline/{nickname}")
    public String get_isOnline(@PathVariable String nickname){
        List<User> _user = repository.findByNickname(nickname);
        boolean isOnline = false;

        if(!_user.isEmpty()){
            isOnline = _user.get(0).getisOnline();

            if(isOnline) {
                return "Online";
            }
            else {
                return "Offline";
            }
        }
        else{
            return "Utente inserito inesistente.";
        }
    }
    //

    // Questo endpoint per un utente il cui nickname viene dato in input prende il valore dei suoi points:
    @GetMapping("/getPoints/{nickname}")
    public int get_Points(@PathVariable String nickname){
        List<User> _user = repository.findByNickname(nickname);
        int points = 0;

        if(!_user.isEmpty()){
            points = _user.get(0).getPoints();

            return points;
        }
        else{
            return -1; // in questo caso il -1 indica che c'è stato un errore, ovvero indica che non esiste un utente nella tabella Utenti che ha
            // quel nickname fornito in input.
        }
    }

    // Questo endpoint serve per poter aggiornare il numero di points di un certo utente identificato tramite il suo "nickname".
    // il valore di {points} dato in input corrisponde al numero di points che dovranno essere aggiunti (se {points} > 0) o tolti
    // (se {points} < 0) all'utente di riferimento.
    @PutMapping("/updatePoints/{nickname}/{points}")
    public String update_points(@PathVariable String nickname, @PathVariable int points){

        List<User> _user = repository.findByNickname(nickname);
        int points_correnti = 0;

        if(!_user.isEmpty()){
            points_correnti = _user.get(0).getPoints();
            _user.get(0).setPoints(points_correnti + points);
            repository.save(_user.get(0)); // memorizzo l'aggiornamento nel DB

            return "Aggiornamento points eseguito con successo.";
        }
        return "Il nickname inserito è errato.";
    }


    // - aggiunta dopo che PIETRO HA FATTO FUNZIONARE KUBERNETIS..
    // Questo endpoint serve per ottenere tutto l'oggetto User che prendo tramite il suo nickname:
    @GetMapping("/getUser/{nickname}")
    public User get_user(@PathVariable String nickname){
        List<User> _user = repository.findByNickname(nickname);
        if(!_user.isEmpty()){
            return _user.get(0); // restituisco l'oggetto user con tutti i dati dell'utente.
        }
        else{
            return null; // vuol dire che il nickname inserito non esiste nel DB.
        }
    }



    // - aggiunta dopo che PIETRO HA FATTO FUNZIONARE KUBERNETIS..
    // Questo endpoint serve per specificare che un utente è andato offline:
    @PutMapping("/setIsOnline/{nickname}")
    public String set_Online(@PathVariable String nickname){
        List<User> _user = repository.findByNickname(nickname);
        if(!_user.isEmpty()){
            _user.get(0).setisOnline(true); // setto il campo isOnline di questo utente a true per far capire che è loggato.
            repository.save(_user.get(0)); // memorizzo in maniera persistente nel DB la modifica fatta allo stato dell'utente.
            return "Settaggio true eseguito.";
        }
        else{
            return "Il nickname inserito è errato.";
        }
    }

    // Questo endpoint serve per specificare che un utente è andato offline:
    @PutMapping("/setIsOffline/{nickname}")
    public String set_Offline(@PathVariable String nickname){
        List<User> _user = repository.findByNickname(nickname);
        if(!_user.isEmpty()){
            _user.get(0).setisOnline(false); // setto il campo isOnline di questo utente a false per far capire che andrà offline.
            repository.save(_user.get(0)); // memorizzo in maniera persistente nel DB la modifica fatta allo stato dell'utente.
            return "Settaggio false eseguito.";
        }
        else{
            return "Il nickname inserito è errato.";
        }
    }



}
