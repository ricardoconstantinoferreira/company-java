package com.company.company.repository;

import com.company.company.model.Titulo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TituloRepository extends MongoRepository<Titulo, String> {
}
