package com.example.collectdata.repository;

import com.example.collectdata.model.Brano;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BranoRep extends CrudRepository<Brano, String> {
    List<Brano> findByNome(String nome_brano);

    Optional<Brano> findById(String id);
}
