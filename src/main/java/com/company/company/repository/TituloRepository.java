package com.company.company.repository;

import com.company.company.model.Pergunta;
import com.company.company.model.Titulo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TituloRepository extends MongoRepository<Titulo, String> {

    @Query(value = "{pergunta:?0}")
    Titulo findByTituloByPergunta(Pergunta pergunta);
}
