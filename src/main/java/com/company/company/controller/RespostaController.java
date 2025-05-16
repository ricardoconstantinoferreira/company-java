package com.company.company.controller;

import com.company.company.dto.RespostaDTO;
import com.company.company.exceptions.Validator;
import com.company.company.model.Resposta;
import com.company.company.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/answer")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    public ResponseEntity<Resposta> save(@RequestBody RespostaDTO respostaDTO) throws Exception {
        Validator validator = new Validator();
        validator.validateRespostas(respostaDTO);

        Resposta resposta = respostaService.save(respostaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
