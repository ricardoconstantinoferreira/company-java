package com.company.company.exceptions;

import com.company.company.dto.EmpresaDTO;
import com.company.company.dto.FuncionarioDTO;
import com.company.company.dto.PerguntaDTO;
import com.company.company.dto.RespostaDTO;

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

        if (perguntasDTO.descricao().isEmpty()) {
            throw new EmptyValueException("Por favor, informe a descrição da pergunta!");
        }

    }

    public void validateRespostas(RespostaDTO respostaDTO) throws EmptyValueException {

        if (respostaDTO.description().isEmpty()) {
            throw new EmptyValueException("Por favor, informe a descrição da resposta!");
        }

    }
 }
