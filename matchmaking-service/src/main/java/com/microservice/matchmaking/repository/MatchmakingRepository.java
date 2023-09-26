package com.microservice.matchmaking.repository;
import com.microservice.matchmaking.model.Matchmaking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MatchmakingRepository extends JpaRepository<Matchmaking, Long> {

    List<Matchmaking> findAll();
    Matchmaking findById(long id);

}
