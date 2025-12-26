package com.company.company.service;

import com.company.company.model.Funcionario;
import com.company.company.repository.FuncionarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Test
    void save_shouldReturnSavedFuncionario() {
        Funcionario f = new Funcionario();
        f.setId("1");
        f.setName("Joao");

        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(f);

        Funcionario result = funcionarioService.save(f);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Joao", result.getName());
        verify(funcionarioRepository, times(1)).save(f);
    }

    @Test
    void getById_shouldReturnFuncionario() {
        Funcionario f = new Funcionario();
        f.setId("1");

        when(funcionarioRepository.findById("1")).thenReturn(Optional.of(f));

        Funcionario result = funcionarioService.getById("1");

        assertEquals("1", result.getId());
        verify(funcionarioRepository, times(1)).findById("1");
    }

    @Test
    void getAll_shouldReturnList() {
        when(funcionarioRepository.findAll()).thenReturn(List.of(new Funcionario(), new Funcionario()));

        List<Funcionario> result = funcionarioService.getAll();

        assertEquals(2, result.size());
        verify(funcionarioRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeByCompany_shouldReturnList() {
        when(funcionarioRepository.findByEmpresaId("company1")).thenReturn(List.of(new Funcionario()));

        List<Funcionario> result = funcionarioService.getEmployeeByCompany("company1");

        assertEquals(1, result.size());
        verify(funcionarioRepository, times(1)).findByEmpresaId("company1");
    }

    @Test
    void deleteById_shouldCallRepository() {
        doNothing().when(funcionarioRepository).deleteById("1");

        funcionarioService.deleteById("1");

        verify(funcionarioRepository, times(1)).deleteById("1");
    }
}
