package com.company.company.repository;

import com.company.company.model.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends MongoRepository<Funcionario, String> {

    List<Funcionario> findByEmpresaId(String id);
}
