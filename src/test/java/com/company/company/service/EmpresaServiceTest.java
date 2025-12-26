package com.company.company.service;

import com.company.company.model.Empresa;
import com.company.company.repository.EmpresaRepository;
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
class EmpresaServiceTest {

    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepository;

    @Test
    void save_shouldReturnSavedEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId("1");
        empresa.setNome_fantasia("Acme");

        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);

        Empresa result = empresaService.save(empresa);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Acme", result.getNome_fantasia());
        verify(empresaRepository, times(1)).save(empresa);
    }

    @Test
    void getAll_shouldReturnList() {
        Empresa e1 = new Empresa();
        Empresa e2 = new Empresa();

        when(empresaRepository.findAll()).thenReturn(List.of(e1, e2));

        List<Empresa> result = empresaService.getAll();

        assertEquals(2, result.size());
        verify(empresaRepository, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId("1");

        when(empresaRepository.findById("1")).thenReturn(Optional.of(empresa));

        Empresa result = empresaService.getById("1");

        assertEquals("1", result.getId());
        verify(empresaRepository, times(1)).findById("1");
    }

    @Test
    void deleteById_shouldCallRepository() {
        doNothing().when(empresaRepository).deleteById("1");

        empresaService.deleteById("1");

        verify(empresaRepository, times(1)).deleteById("1");
    }
}
