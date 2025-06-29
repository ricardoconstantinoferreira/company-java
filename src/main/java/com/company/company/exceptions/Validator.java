package com.company.company.exceptions;

import com.company.company.dto.*;
import com.company.company.model.Pergunta;

import java.util.List;

public class Validator {

    public void validateEmpresa(EmpresaDTO empresaDTO) throws EmptyValueException {
        if (empresaDTO.razao_social().isEmpty()) {
            throw new EmptyValueException("Por favor, informe a Razão Social!");
        }

        if (empresaDTO.nome_fantasia().isEmpty()) {
            throw new EmptyValueException("Por favor, informe o nome fantasia!");
        }

        if (empresaDTO.cnpj().isEmpty()) {
            throw new EmptyValueException("Por favor, informe o CNPJ!");
        }
    }

    public void validateFuncionario(FuncionarioDTO funcionarioDTO) throws EmptyValueException {
        if (funcionarioDTO.name().isEmpty()) {
            throw new EmptyValueException("Por favor, informe o nome do funcionário!");
        }

        if (funcionarioDTO.cargo().isEmpty()) {
            throw new EmptyValueException("Por favor, informe o cargo do funcionário!");
        }

        if (funcionarioDTO.empresa_id().isEmpty()) {
            throw new EmptyValueException("Por favor, informe a empresa do funcionário!");
        }
    }

    public void validatePerguntas(PerguntaDTO perguntasDTO) throws EmptyValueException {
        List<Pergunta> descricao = perguntasDTO.pergunta();

        for (Pergunta desc: descricao) {
            if (desc.getDescricao().isEmpty()) {
                throw new EmptyValueException("Por favor, pergunta obrigatória!");
            }
        }
    }

    public void validateRespostas(RespostaDTO respostaDTO) throws EmptyValueException {
        if (respostaDTO.perguntas().size() == 0) {
            throw new EmptyValueException("Por favor, informe a descrição da resposta!");
        }
    }

    public void validateTitulos(TituloDTO tituloDTO) throws EmptyValueException {
        if (tituloDTO.descricao().isEmpty()) {
            throw new EmptyValueException("Por favor, informe a descrição do titulo!");
        }
    }
 }
