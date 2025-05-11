package com.company.company.service;

import com.company.company.model.Empresa;
import com.company.company.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public List<Empresa> getAll() {
        return empresaRepository.findAll();
    }

    public Empresa getById(String id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        return empresa.get();
    }

    public void deleteById(String id) {
        empresaRepository.deleteById(id);
    }
}
