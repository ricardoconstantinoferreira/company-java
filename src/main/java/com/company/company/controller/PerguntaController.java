package com.company.company.controller;

import com.company.company.dto.PerguntaDTO;
import com.company.company.exceptions.Validator;
import com.company.company.model.Pergunta;
import com.company.company.service.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/question")
public class PerguntaController {

    @Autowired
    private PerguntaService perguntaService;

    @GetMapping
    public ResponseEntity<List<Pergunta>> getAll() {
        List<Pergunta> perguntas = perguntaService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(perguntas);
    }

    @PostMapping
    public ResponseEntity<Pergunta> save(@RequestBody PerguntaDTO perguntaDTO) {
        Pergunta pergunta = new Pergunta();
        Validator validate = new Validator();

        validate.validatePerguntas(perguntaDTO);

        pergunta.setDescricao(perguntaDTO.descricao());
        Pergunta perguntaResult = perguntaService.save(pergunta);
        return ResponseEntity.status(HttpStatus.OK).body(perguntaResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pergunta> update(@PathVariable(value = "id") String id, @RequestBody PerguntaDTO perguntaDTO) {
        Pergunta pergunta = new Pergunta();
        Validator validate = new Validator();
        validate.validatePerguntas(perguntaDTO);

        Pergunta oldResult = perguntaService.getById(id);
        pergunta.setId(oldResult.getId());
        pergunta.setDescricao(perguntaDTO.descricao());

        Pergunta perguntaResult = perguntaService.save(pergunta);
        return ResponseEntity.status(HttpStatus.OK).body(perguntaResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> getById(@PathVariable String id) {
        Pergunta pergunta =  perguntaService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(pergunta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pergunta> deleteById(@PathVariable(value = "id") String id) {
        Pergunta pergunta = perguntaService.getById(id);

        if (!pergunta.getId().isEmpty()) {
            perguntaService.deleteById(id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(pergunta);
    }
}
