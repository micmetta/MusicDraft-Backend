package com.example.collectdata.repository;

import com.example.collectdata.model.Brano;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BranoRep extends CrudRepository<Brano,Long> {
    List<Brano> findByNome(String nome_brano);
}
