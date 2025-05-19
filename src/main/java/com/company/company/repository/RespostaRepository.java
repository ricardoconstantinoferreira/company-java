package com.company.company.repository;

import com.company.company.model.Resposta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaRepository extends MongoRepository<Resposta, String> {
}
