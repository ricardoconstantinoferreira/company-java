package com.company.company.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "titulos")
public class Titulo {

    @Id
    private String id;

    @Field(value = "descricao")
    private String descricao;

    private List<Pergunta> pergunta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Pergunta> getPergunta() {
        return pergunta;
    }

    public void setPergunta(List<Pergunta> pergunta) {
        this.pergunta = pergunta;
    }
}
