package com.company.company.repository;

import com.company.company.model.Pergunta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerguntaRepository extends MongoRepository<Pergunta, String> {
}
