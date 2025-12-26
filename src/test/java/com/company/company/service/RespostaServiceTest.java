package com.company.company.service;

import com.company.company.dto.RespostaDTO;
import com.company.company.model.*;
import com.company.company.repository.EmpresaRepository;
import com.company.company.repository.FuncionarioRepository;
import com.company.company.repository.PerguntaRepository;
import com.company.company.repository.RespostaRepository;
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
class RespostaServiceTest {

    @InjectMocks
    private RespostaService respostaService;

    @Mock
    private RespostaRepository respostaRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private PerguntaRepository perguntaRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private TituloService tituloService;

    @Test
    void save_shouldPersistRespostas() throws Exception {
        Pergunta pergunta = new Pergunta();
        pergunta.setId("p1");

        Funcionario funcionario = new Funcionario();
        funcionario.setId("f1");

        PerguntaResposta pr = new PerguntaResposta();
        pr.setId("p1");
        pr.setResposta("Resposta X");

        RespostaDTO dto = new RespostaDTO("f1", List.of(pr));

        when(perguntaRepository.findById("p1")).thenReturn(Optional.of(pergunta));
        when(funcionarioRepository.findById("f1")).thenReturn(Optional.of(funcionario));
        when(respostaRepository.save(any(Resposta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Resposta> result = respostaService.save(dto, "");

        assertEquals(1, result.size());
        assertEquals("Resposta X", result.get(0).getDescription());
        assertEquals("f1", result.get(0).getFuncionario().getId());
        verify(respostaRepository, times(1)).save(any(Resposta.class));
    }

    @Test
    void save_whenPerguntaNotFound_shouldThrow() {
        PerguntaResposta pr = new PerguntaResposta();
        pr.setId("p1");

        RespostaDTO dto = new RespostaDTO("f1", List.of(pr));

        when(perguntaRepository.findById("p1")).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> respostaService.save(dto, ""));
        assertTrue(ex.getMessage().contains("Pergunta não existe"));
    }

    @Test
    void save_whenFuncionarioNotFound_shouldThrow() {
        Pergunta pergunta = new Pergunta();
        pergunta.setId("p1");

        PerguntaResposta pr = new PerguntaResposta();
        pr.setId("p1");

        RespostaDTO dto = new RespostaDTO("f1", List.of(pr));

        when(perguntaRepository.findById("p1")).thenReturn(Optional.of(pergunta));
        when(funcionarioRepository.findById("f1")).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> respostaService.save(dto, ""));
        assertTrue(ex.getMessage().contains("Funcionário não existe"));
    }

    @Test
    void getAnswerByQuestionById_shouldReturnMatchingResposta() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId("f1");

        Pergunta p = new Pergunta();
        p.setId("p1");

        Resposta r = new Resposta();
        r.setPergunta(p);

        when(funcionarioRepository.findById("f1")).thenReturn(Optional.of(funcionario));
        when(respostaRepository.findByRespostaByFuncionario(funcionario)).thenReturn(List.of(r));

        Resposta result = respostaService.getAnswerByQuestionById("p1", "f1");

        assertNotNull(result);
        assertEquals("p1", result.getPergunta().getId());
    }

    @Test
    void deleteById_shouldDeleteAndReturnResposta() {
        Resposta r = new Resposta();
        r.setId("r1");

        when(respostaRepository.findById("r1")).thenReturn(Optional.of(r));
        doNothing().when(respostaRepository).deleteById("r1");

        Resposta result = respostaService.deleteById("r1");

        assertEquals("r1", result.getId());
        verify(respostaRepository, times(1)).deleteById("r1");
    }

    @Test
    void getAnswerByCompany_shouldReturnAggregatedResults() {
        Empresa empresa = new Empresa();
        empresa.setId("e1");
        empresa.setNome_fantasia("Empresa X");

        Funcionario func = new Funcionario();
        func.setId("f1");

        Pergunta p1 = new Pergunta();
        p1.setId("p1");
        Pergunta p2 = new Pergunta();
        p2.setId("p2");

        Resposta r1 = new Resposta();
        r1.setPergunta(p1);
        Resposta r2 = new Resposta();
        r2.setPergunta(p2);

        when(empresaRepository.findAll()).thenReturn(List.of(empresa));
        when(funcionarioRepository.findByEmpresaId("e1")).thenReturn(List.of(func));
        when(respostaRepository.findByRespostaByFuncionario(func)).thenReturn(List.of(r1, r2));
        when(tituloService.getTitleByQuestion(anyList())).thenReturn(List.of("T1", "T2"));

        List<RespostasEmpresa> result = respostaService.getAnswerByCompany();

        assertEquals(1, result.size());
        assertEquals("Empresa X", result.get(0).getEmpresaNome());
        assertEquals(Long.valueOf(2L), result.get(0).getTotalRespostas());
    }
}
