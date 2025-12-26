package com.company.company.service;

import com.company.company.dto.TituloDTO;
import com.company.company.model.*;
import com.company.company.repository.FuncionarioRepository;
import com.company.company.repository.PerguntaRepository;
import com.company.company.repository.RespostaRepository;
import com.company.company.repository.TituloRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TituloServiceTest {

    @InjectMocks
    private TituloService tituloService;

    @Mock
    private TituloRepository tituloRepository;

    @Mock
    private PerguntaRepository perguntaRepository;

    @Mock
    private RespostaRepository respostaRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Test
    void save_shouldPersistTitulo() throws Exception {
        Pergunta p1 = new Pergunta();
        p1.setId("p1");

        TituloDTO dto = new TituloDTO("Titulo A", List.of("p1"));

        when(perguntaRepository.findById("p1")).thenReturn(Optional.of(p1));
        when(tituloRepository.save(any(Titulo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Titulo result = tituloService.save(dto, "");

        assertEquals("Titulo A", result.getDescricao());
        assertEquals(1, result.getPergunta().size());
        verify(tituloRepository, times(1)).save(any(Titulo.class));
    }

    @Test
    void save_whenPerguntaNotFound_shouldThrow() {
        TituloDTO dto = new TituloDTO("Titulo A", List.of("p1"));

        when(perguntaRepository.findById("p1")).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> tituloService.save(dto, ""));
        assertTrue(ex.getMessage().contains("Pergunta não existe"));
    }

    @Test
    void getTitleByQuestion_shouldReturnDistinctList() {
        Pergunta p1 = new Pergunta();
        p1.setId("p1");
        Pergunta p2 = new Pergunta();
        p2.setId("p2");

        Titulo t = new Titulo();
        t.setDescricao("Titulo comum");

        when(tituloRepository.findByTituloByPergunta(p1)).thenReturn(t);
        when(tituloRepository.findByTituloByPergunta(p2)).thenReturn(t);

        List<String> result = tituloService.getTitleByQuestion(List.of(p1, p2));

        assertEquals(1, result.size());
        assertEquals("Titulo comum", result.get(0));
    }

    @Test
    void getTitleByEmployee_shouldReturnMap() {
        Funcionario func = new Funcionario();
        func.setId("f1");

        Pergunta p = new Pergunta();
        p.setId("p1");

        Resposta r = new Resposta();
        r.setPergunta(p);

        Titulo t = new Titulo();
        t.setId("t1");
        t.setDescricao("T1");
        t.setPergunta(List.of(p));

        when(funcionarioRepository.findById("f1")).thenReturn(Optional.of(func));
        when(respostaRepository.findByRespostaByFuncionario(func)).thenReturn(List.of(r));
        when(tituloRepository.findAll()).thenReturn(List.of(t));

        HashMap<String, String> result = tituloService.getTitleByEmployee("f1");

        assertEquals(1, result.size());
        assertTrue(result.containsKey("t1"));
        assertEquals("T1", result.get("t1"));
    }

    @Test
    void getTitleByEmployeeAll_shouldReturnList() {
        Funcionario func = new Funcionario();
        func.setId("f1");
        func.setName("Joao");

        Pergunta p = new Pergunta();
        p.setId("p1");

        Resposta r = new Resposta();
        r.setPergunta(p);

        Titulo t = new Titulo();
        t.setId("t1");
        t.setDescricao("T1");
        t.setPergunta(List.of(p));

        when(funcionarioRepository.findAll()).thenReturn(List.of(func));
        when(respostaRepository.findByRespostaByFuncionario(func)).thenReturn(List.of(r));
        when(tituloRepository.findAll()).thenReturn(List.of(t));

        var result = tituloService.getTitleByEmployeeAll();

        assertEquals(1, result.size());
        assertEquals("Joao", result.get(0).getFuncionarioNome());
        assertEquals(Long.valueOf(1L), result.get(0).getTotalRespostas());
    }

    @Test
    void getAll_shouldReturnList() {
        when(tituloRepository.findAll()).thenReturn(List.of(new Titulo(), new Titulo()));

        List<Titulo> result = tituloService.getAll();

        assertEquals(2, result.size());
        verify(tituloRepository, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnTitulo() {
        Titulo t = new Titulo();
        t.setId("t1");
        t.setDescricao("Desc");

        when(tituloRepository.findById("t1")).thenReturn(Optional.of(t));

        Titulo result = tituloService.getById("t1");

        assertEquals("t1", result.getId());
        assertEquals("Desc", result.getDescricao());
        verify(tituloRepository, times(1)).findById("t1");
    }

    @Test
    void delete_shouldDeleteAndReturnTitulo() {
        Titulo t = new Titulo();
        t.setId("t1");
        t.setDescricao("Desc");

        when(tituloRepository.findById("t1")).thenReturn(Optional.of(t));
        doNothing().when(tituloRepository).delete(t);

        Titulo result = tituloService.delete("t1");

        assertEquals("Desc", result.getDescricao());
        verify(tituloRepository, times(1)).delete(t);
    }
}
