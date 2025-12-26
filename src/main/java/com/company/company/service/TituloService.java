package com.company.company.service;

import com.company.company.dto.TituloDTO;
import com.company.company.model.Funcionario;
import com.company.company.model.Pergunta;
import com.company.company.model.Resposta;
import com.company.company.model.Titulo;
import com.company.company.repository.FuncionarioRepository;
import com.company.company.repository.PerguntaRepository;
import com.company.company.repository.RespostaRepository;
import com.company.company.repository.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TituloService {

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

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

    public List<Titulo> getAll() {
        return tituloRepository.findAll();
    }

    public Titulo getById(String id) {
        Optional<Titulo> titulo = tituloRepository.findById(id);
        return titulo.get();
    }

    public Titulo delete(String id) {
        Titulo titulo = tituloRepository.findById(id).get();

        if (!titulo.getDescricao().isEmpty()) {
            tituloRepository.delete(titulo);
        }

        return titulo;
    }

    public HashMap<String, String> getTitleByEmployee(String id) {
        Funcionario funcionario = funcionarioRepository.findById(id).get();
        List<Resposta> respostas = respostaRepository.findByRespostaByFuncionario(funcionario);
        List<String> perguntas = new ArrayList<>();
        HashMap<String, String> titulosMap = new HashMap<>();

        for (Resposta resposta: respostas) {
            perguntas.add(resposta.getPergunta().getId());
        }

        List<Titulo> titulos = tituloRepository.findAll();

        for (Titulo titulo: titulos) {
            for (Pergunta pergunta: titulo.getPergunta()) {
                if (perguntas.contains(pergunta.getId())) {
                    titulosMap.put(titulo.getId(), titulo.getDescricao());
                }
            }
        }

        return titulosMap;
    }

    public List<String> getTitleByQuestion(List<Pergunta> perguntas) {
        List<String> descTitulo = new ArrayList<>();

        for (Pergunta pergunta: perguntas) {
            Titulo titulo = tituloRepository.findByTituloByPergunta(pergunta);
            descTitulo.add(titulo.getDescricao());
        }

        List<String> descTituloClear = descTitulo.stream().distinct().collect(Collectors.toUnmodifiableList());
        return descTituloClear;
    }

}
