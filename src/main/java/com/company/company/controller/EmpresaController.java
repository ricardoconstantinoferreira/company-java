package com.company.company.controller;

import com.company.company.dto.EmpresaDTO;
import com.company.company.exceptions.EmptyValueException;
import com.company.company.exceptions.Validator;
import com.company.company.model.Empresa;
import com.company.company.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/company")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getById(@PathVariable(value = "id") String id) {
        Empresa empresa = empresaService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(
            @PathVariable(value = "id") String id,
            @RequestBody EmpresaDTO empresaDTO
    ) throws EmptyValueException {
        Empresa empresa = new Empresa();
        Validator validate = new Validator();

        validate.validateEmpresa(empresaDTO);

        empresa.setId(id);
        empresa.setRazao_social(empresaDTO.razao_social());
        empresa.setNome_fantasia(empresaDTO.nome_fantasia());
        empresa.setCnpj(empresaDTO.cnpj());

        empresa = empresaService.save(empresa);

        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> getAll() {
        List<Empresa> empresas = empresaService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(empresas);
    }

    @PostMapping
    public ResponseEntity<Empresa> save(@RequestBody EmpresaDTO empresaDTO) throws EmptyValueException {
        Empresa empresa = new Empresa();
        Validator validate = new Validator();

        validate.validateEmpresa(empresaDTO);

        empresa.setRazao_social(empresaDTO.razao_social());
        empresa.setNome_fantasia(empresaDTO.nome_fantasia());
        empresa.setCnpj(empresaDTO.cnpj());

        empresa = empresaService.save(empresa);

        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> delete(@PathVariable(value = "id") String id) {
        Empresa empresa = empresaService.getById(id);

        if (!empresa.getId().isEmpty()) {
            empresaService.deleteById(id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }
}
