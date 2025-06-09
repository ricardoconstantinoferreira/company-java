package com.company.company.service;

import com.company.company.dto.PerguntaDTO;
import com.company.company.model.Pergunta;
import com.company.company.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;

    public List<Pergunta> save(PerguntaDTO perguntaDTO) {
        List<Pergunta> perguntas = perguntaDTO.pergunta();
        List<Pergunta> arrayPergunta = new ArrayList<>();

        for (Pergunta perg: perguntas) {
            Pergunta pergunta1 = new Pergunta();

            if (!perg.getId().isEmpty()) {
                pergunta1.setId(perg.getId());
            }

            pergunta1.setDescricao(perg.getDescricao());
            Pergunta resultado = perguntaRepository.save(pergunta1);
            arrayPergunta.add(resultado);
        }

        return arrayPergunta;
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
