package com.company.company.repository;

import com.company.company.model.Funcionario;
import com.company.company.model.Resposta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespostaRepository extends MongoRepository<Resposta, String> {

    @Query(value = "{funcionario:?0}")
    List<Resposta> findByRespostaByFuncionario(Funcionario funcionario);
}
