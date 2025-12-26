package com.company.company.model;

public class RespostasFuncionario {
    private String funcionarioNome;

    private Long totalRespostas;

    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }

    public Long getTotalRespostas() {
        return totalRespostas;
    }

    public void setTotalRespostas(Long totalRespostas) {
        this.totalRespostas = totalRespostas;
    }
}
