package com.company.company.service;

import com.company.company.model.Pergunta;
import com.company.company.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;

    public Pergunta save(Pergunta pergunta) {
        return perguntaRepository.save(pergunta);
    }

    public List<Pergunta> getAll() {
        return perguntaRepository.findAll();
    }

    public Pergunta getById(String id) {
        return perguntaRepository.findById(id).get();
    }

    public void deleteById(String id) {
        perguntaRepository.deleteById(id);
    }
}
