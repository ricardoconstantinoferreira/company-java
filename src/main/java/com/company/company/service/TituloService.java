package com.company.company.service;

import com.company.company.dto.TituloDTO;
import com.company.company.model.Pergunta;
import com.company.company.model.Titulo;
import com.company.company.repository.PerguntaRepository;
import com.company.company.repository.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TituloService {

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    public Titulo save(TituloDTO tituloDTO, String id) throws Exception {
        Titulo titulo = new Titulo();
        List<String> perguntas = tituloDTO.perguntas();
        List<Pergunta> perguntaArray = new ArrayList<>();

        for (String pergunta_id: perguntas) {
            Optional<Pergunta> pergunta = perguntaRepository.findById(pergunta_id);

            if (pergunta.isEmpty()) {
                throw new Exception("Pergunta não existe!");
            }

            perguntaArray.add(pergunta.get());
        }

        if (!id.isEmpty()) {
            titulo.setId(id);
        }

        titulo.setDescricao(tituloDTO.descricao());
        titulo.setPergunta(perguntaArray);

        return tituloRepository.save(titulo);
    }

}
