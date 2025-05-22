package com.company.company.controller;

import com.company.company.dto.TituloDTO;
import com.company.company.exceptions.Validator;
import com.company.company.model.Titulo;
import com.company.company.service.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/title")
public class TituloController {

    @Autowired
    private TituloService tituloService;

    @PostMapping
    public ResponseEntity<Titulo> save(@RequestBody TituloDTO tituloDTO) throws Exception {
        Validator validator = new Validator();
        validator.validateTitutlos(tituloDTO);

        Titulo titulo = tituloService.save(tituloDTO, "");
        return ResponseEntity.status(HttpStatus.OK).body(titulo);
    }
}
