package com.company.company.service;

import com.company.company.model.*;
import com.company.company.repository.FuncionarioRepository;
import com.company.company.repository.RespostaRepository;
import com.company.company.repository.TituloRepository;
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
class FormularioServiceTest {

    @InjectMocks
    private FormularioService formularioService;

    @Mock
    private TituloRepository tituloRepository;

    @Mock
    private RespostaRepository respostaRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Test
    void getForms_shouldReturnFormulario() throws Exception {
        Titulo titulo = new Titulo();
        titulo.setId("t1");
        titulo.setDescricao("Titulo A");
        Pergunta p = new Pergunta();
        p.setId("p1");
        titulo.setPergunta(List.of(p));

        Funcionario func = new Funcionario();
        func.setId("f1");
        func.setName("Maria");

        Resposta r = new Resposta();
        r.setPergunta(p);

        when(tituloRepository.findById("t1")).thenReturn(Optional.of(titulo));
        when(funcionarioRepository.findById("f1")).thenReturn(Optional.of(func));
        when(respostaRepository.findByRespostaByFuncionario(func)).thenReturn(List.of(r));

        Formulario result = formularioService.getForms("t1", "f1");

        assertEquals("Titulo A", result.getTitulo());
        assertEquals("Maria", result.getFuncionario());
        assertEquals(1, result.getResposta().size());
    }

    @Test
    void getForms_whenTituloHasNoPerguntas_shouldThrow() {
        Titulo titulo = new Titulo();
        titulo.setId("t1");
        titulo.setPergunta(List.of());

        when(tituloRepository.findById("t1")).thenReturn(Optional.of(titulo));

        Exception ex = assertThrows(Exception.class, () -> formularioService.getForms("t1", "f1"));
        assertTrue(ex.getMessage().contains("No value present"));
    }

    @Test
    void getForms_whenFuncionarioNameEmpty_shouldThrow() {
        Titulo titulo = new Titulo();
        titulo.setId("t1");
        Pergunta p = new Pergunta();
        p.setId("p1");
        titulo.setPergunta(List.of(p));

        Funcionario func = new Funcionario();
        func.setId("f1");
        func.setName("");

        when(tituloRepository.findById("t1")).thenReturn(Optional.of(titulo));
        when(funcionarioRepository.findById("f1")).thenReturn(Optional.of(func));

        Exception ex = assertThrows(Exception.class, () -> formularioService.getForms("t1", "f1"));
        assertTrue(ex.getMessage().contains("Não existe funcionário"));
    }
}
