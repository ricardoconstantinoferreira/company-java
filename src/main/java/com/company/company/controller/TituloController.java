package com.company.company.controller;

import com.company.company.dto.TituloDTO;
import com.company.company.exceptions.Validator;
import com.company.company.model.Titulo;
import com.company.company.service.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "api/title")
public class TituloController {

    @Autowired
    private TituloService tituloService;

    @PostMapping
    public ResponseEntity<Titulo> save(@RequestBody TituloDTO tituloDTO) throws Exception {
        Validator validator = new Validator();
        validator.validateTitulos(tituloDTO);

        Titulo titulo = tituloService.save(tituloDTO, "");
        return ResponseEntity.status(HttpStatus.OK).body(titulo);
    }

    @GetMapping
    public ResponseEntity<List<Titulo>> getAll() {
        List<Titulo> titulos = tituloService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(titulos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Titulo> getById(@PathVariable(value = "id") String id) {
        Titulo titulo = tituloService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(titulo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Titulo> update(
            @PathVariable(value = "id") String id,
            @RequestBody TituloDTO tituloDTO) throws Exception {
        Validator validator = new Validator();
        validator.validateTitulos(tituloDTO);

        Titulo titulo = tituloService.save(tituloDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(titulo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Titulo> delete(@PathVariable(value = "id") String id) {
        Titulo titulo = tituloService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(titulo);
    }

    @GetMapping("/get-title-by-employee/{id}")
    public ResponseEntity<HashMap<String, String>> getTitleByEmployee(@PathVariable(value = "id") String id) {
        HashMap<String, String> titulos = tituloService.getTitleByEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body(titulos);
    }
}
