package com.company.company.controller;

import com.company.company.dto.FuncionarioDTO;
import com.company.company.exceptions.EmptyValueException;
import com.company.company.exceptions.Validator;
import com.company.company.model.Empresa;
import com.company.company.model.Funcionario;
import com.company.company.service.EmpresaService;
import com.company.company.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "api/employee")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Funcionario> save(@RequestBody FuncionarioDTO funcionarioDTO) throws EmptyValueException {
        Funcionario funcionario = new Funcionario();
        Validator validate = new Validator();

        validate.validateFuncionario(funcionarioDTO);

        Empresa empresa = empresaService.getById(funcionarioDTO.empresa_id());

        funcionario.setName(funcionarioDTO.name());
        funcionario.setCargo(funcionarioDTO.cargo());
        funcionario.setEmpresa(empresa);

        funcionario = funcionarioService.save(funcionario);

        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @GetMapping("/teste")
    public HashMap<Integer, String>  getMap() {
        String[] cars = {"Renault Logan", "Renault Clio", "Gol", "Fusca", "Ford Ka"};
        HashMap<Integer, String> carList = new HashMap<>();

        for (int i = 0; i < cars.length; i++) {
            carList.put(i, cars[i]);
        }

        return carList;

    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(
            @PathVariable(value = "id") String id,
            @RequestBody FuncionarioDTO funcionarioDTO
    ) {
        Funcionario funcionario = new Funcionario();
        Validator validate = new Validator();

        validate.validateFuncionario(funcionarioDTO);

        Empresa empresa = empresaService.getById(funcionarioDTO.empresa_id());

        funcionario.setId(id);
        funcionario.setName(funcionarioDTO.name());
        funcionario.setCargo(funcionarioDTO.cargo());
        funcionario.setEmpresa(empresa);

        funcionario = funcionarioService.save(funcionario);

        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getById(@PathVariable(value = "id") String id) {
        Funcionario funcionario = funcionarioService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAll() {
        List<Funcionario> funcionarios = funcionarioService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Funcionario> delete(@PathVariable(value = "id") String id) {
        Funcionario funcionario = funcionarioService.getById(id);

        if (!funcionario.getId().isEmpty()) {
            funcionarioService.deleteById(id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }
}
