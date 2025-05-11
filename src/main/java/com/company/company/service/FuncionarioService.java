package com.company.company.service;

import com.company.company.model.Funcionario;
import com.company.company.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario getById(String id) {
        return funcionarioRepository.findById(id).get();
    }

    public List<Funcionario> getAll() {
        return funcionarioRepository.findAll();
    }

    public List<Funcionario> getEmployeeByCompany(String id) {
        return funcionarioRepository.findByEmpresaId(id);
    }

    public void deleteById(String id) {
        funcionarioRepository.deleteById(id);
    }
}
