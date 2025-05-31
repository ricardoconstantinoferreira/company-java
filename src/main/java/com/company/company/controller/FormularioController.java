package com.company.company.controller;

import com.company.company.model.Formulario;
import com.company.company.service.FormularioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/forms")
public class FormularioController {

    @Autowired
    private FormularioService formularioService;

    @GetMapping("/{titulo_id}/{funcionario_id}")
    public ResponseEntity<Formulario> getFormsByPerguntaIdAndFuncionarioId(
            @PathVariable(value = "titulo_id") String titulo_id,
            @PathVariable(value = "funcionario_id") String funcionario_id
    ) throws Exception {
        Formulario formulario = formularioService.getForms(titulo_id, funcionario_id);
        return ResponseEntity.status(HttpStatus.OK).body(formulario);
    }
}
