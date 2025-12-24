package com.company.company.service;

import com.company.company.dto.RespostaDTO;
import com.company.company.model.*;
import com.company.company.repository.EmpresaRepository;
import com.company.company.repository.FuncionarioRepository;
import com.company.company.repository.PerguntaRepository;
import com.company.company.repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TituloService tituloService;

    public List<Resposta> save(RespostaDTO respostaDTO, String id) throws Exception {
        List<Resposta> resultado = new ArrayList<>();
        List<PerguntaResposta> perguntas = respostaDTO.perguntas();

        for (PerguntaResposta perg: perguntas) {
            Resposta resposta = new Resposta();
            Optional<Pergunta> pergunta = perguntaRepository.findById(perg.getId());
            Optional<Funcionario> funcionario = funcionarioRepository.findById(respostaDTO.funcionario_id());

            if (pergunta.isEmpty()) {
                throw new Exception("Pergunta não existe!");
            }

            if (funcionario.isEmpty()) {
                throw new Exception("Funcionário não existe!");
            }

            if (!id.isEmpty()) {
                resposta.setId(id);
            }

            resposta.setDescription(perg.getResposta());
            resposta.setFuncionario(funcionario.get());
            resposta.setPergunta(pergunta.get());
            Resposta result = respostaRepository.save(resposta);
            resultado.add(result);
        }

        return resultado;
    }

    public List<Resposta> getAll() {
        return respostaRepository.findAll();
    }

    public Resposta getAnswerByQuestionById(String perguntaId, String funcionarioId) {
        Resposta respostaResult = null;
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId).get();
        List<Resposta> respostas =  respostaRepository.findByRespostaByFuncionario(funcionario);

        for(Resposta resposta: respostas) {
            if (resposta.getPergunta().getId().equals(perguntaId)) {
                respostaResult = resposta;
            }
        }

        return respostaResult;
    }

    public Resposta getById(String id) {
        return respostaRepository.findById(id).get();
    }

    public Resposta deleteById(String id) {
        Resposta resposta = respostaRepository.findById(id).get();

        if (!resposta.getId().isEmpty()) {
            respostaRepository.deleteById(id);
        }

        return resposta;
    }

    public List<RespostasEmpresa> getAnswerByCompany() {
        ArrayList<RespostasEmpresa> respostasEmpresasElements = new ArrayList<>();
        List<Pergunta> perguntas = new ArrayList<>();

        List<Empresa> empresas = empresaRepository.findAll();

        if (!empresas.isEmpty()) {
            for (Empresa empresa: empresas) {
                List<Funcionario> funcionarios = funcionarioRepository.findByEmpresaId(empresa.getId());

                if (!funcionarios.isEmpty()) {
                    for (Funcionario funcionario : funcionarios) {
                        List<Resposta> respostas = respostaRepository.findByRespostaByFuncionario(funcionario);

                        for (Resposta resposta : respostas) {
                            perguntas.add(resposta.getPergunta());
                        }
                    }

                    List<String> resultadoPerguntas = tituloService.getTitleByQuestion(perguntas, funcionarios);

                    RespostasEmpresa respostasEmpresa = new RespostasEmpresa();
                    respostasEmpresa.setEmpresaNome(empresa.getNome_fantasia());
                    respostasEmpresa.setTotalRespostas(Long.valueOf(resultadoPerguntas.size()));

                    respostasEmpresasElements.add(respostasEmpresa);
                    perguntas = new ArrayList<>();
                }
            }
        }

        return respostasEmpresasElements;

    }
}
