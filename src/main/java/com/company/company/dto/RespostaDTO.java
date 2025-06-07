package com.company.company.dto;

import com.company.company.model.PerguntaResposta;

import java.util.List;

public record RespostaDTO (
        String funcionario_id,
        List<PerguntaResposta> perguntas
) {}
