package com.company.company.service;

import com.company.company.model.*;
import com.company.company.repository.FuncionarioRepository;
import com.company.company.repository.RespostaRepository;
import com.company.company.repository.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormularioService {

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Formulario getForms(String titulo_id, String funcionario_id) throws Exception {
        Formulario formulario = new Formulario();
        Titulo titulo = tituloRepository.findById(titulo_id).get();
        Funcionario funcionario = funcionarioRepository.findById(funcionario_id).get();

        List<Pergunta> perguntas = titulo.getPergunta();
        List<Resposta> respostas = respostaRepository.findByRespostaByFuncionario(funcionario);

        if (titulo.getPergunta().isEmpty()) {
            throw new Exception("Não existe formulario!");
        }

        if (funcionario.getName().isEmpty()) {
            throw new Exception("Não existe funcionário!");
        }

        List<Resposta> respostasPergunta = getRespostas(respostas, perguntas);

        formulario.setTitulo(titulo.getDescricao());
        formulario.setFuncionario(funcionario.getName());
        formulario.setResposta(respostasPergunta);

        return formulario;
    }

    private List<Resposta> getRespostas(List<Resposta> respostas, List<Pergunta> perguntas) {
        List<Resposta> respostasPergunta = new ArrayList<>();

        for (Pergunta pergunta: perguntas) {
            for (Resposta resposta : respostas) {
                if (pergunta.getId().equals(resposta.getPergunta().getId())) {
                    respostasPergunta.add(resposta);
                }
            }
        }

        return respostasPergunta;
    }
}
