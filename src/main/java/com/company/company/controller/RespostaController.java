package com.company.company.controller;

import com.company.company.dto.RespostaDTO;
import com.company.company.exceptions.Validator;
import com.company.company.model.Resposta;
import com.company.company.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/answer")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    public ResponseEntity<List<Resposta>> save(@RequestBody RespostaDTO respostaDTO) throws Exception {
        Validator validator = new Validator();
        validator.validateRespostas(respostaDTO);

        List<Resposta> resposta = respostaService.save(respostaDTO, "");
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<Resposta>> getAll() {
        List<Resposta> respostas = respostaService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(respostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> getById(@PathVariable(value = "id") String id) {
        Resposta resposta = respostaService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Resposta>> update(
            @PathVariable(value = "id") String id,
            @RequestBody RespostaDTO respostaDTO
    ) throws Exception {
        Validator validator = new Validator();
        validator.validateRespostas(respostaDTO);

        List<Resposta> resposta = respostaService.save(respostaDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resposta> delete(@PathVariable(value = "id") String id) {
        Resposta resposta = respostaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @GetMapping("/answer-by-question-id/{id}")
    public ResponseEntity<Resposta> getAnswerByQuestionById(@PathVariable(value = "id") String id) {
        Resposta resposta = respostaService.getAnswerByQuestionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
