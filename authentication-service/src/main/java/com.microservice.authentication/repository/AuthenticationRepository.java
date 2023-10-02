package com.microservice.authentication.repository;
import com.microservice.authentication.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface AuthenticationRepository extends CrudRepository<User, Long> {


    // Questa funzione controlla se l'utente con i due parametri passati qui sotto è registrato nel db_utenti o meno.
    List<User> findByNickname(String nickname);
    List<User> findByEmail(String email);
    User findByNicknameAndPassword(String nickname, String password);
    User findByEmailAndPassword(String email, String password);

    List<User> findAll();

}
