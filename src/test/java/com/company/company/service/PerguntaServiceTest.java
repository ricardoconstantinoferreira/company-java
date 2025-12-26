package com.company.company.service;

import com.company.company.dto.PerguntaDTO;
import com.company.company.model.Pergunta;
import com.company.company.repository.PerguntaRepository;
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
class PerguntaServiceTest {

    @InjectMocks
    private PerguntaService perguntaService;

    @Mock
    private PerguntaRepository perguntaRepository;

    @Test
    void save_shouldPersistPerguntas() {
        Pergunta p1 = new Pergunta();
        p1.setId("");
        p1.setDescricao("Desc1");

        Pergunta p2 = new Pergunta();
        p2.setId("2");
        p2.setDescricao("Desc2");

        when(perguntaRepository.save(any(Pergunta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Pergunta> result = perguntaService.save(new PerguntaDTO(List.of(p1, p2)));

        assertEquals(2, result.size());
        assertEquals("Desc1", result.get(0).getDescricao());
        verify(perguntaRepository, times(2)).save(any(Pergunta.class));
    }

    @Test
    void getAll_shouldReturnList() {
        when(perguntaRepository.findAll()).thenReturn(List.of(new Pergunta(), new Pergunta()));

        List<Pergunta> result = perguntaService.getAll();

        assertEquals(2, result.size());
        verify(perguntaRepository, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnPergunta() {
        Pergunta p = new Pergunta();
        p.setId("1");

        when(perguntaRepository.findById("1")).thenReturn(Optional.of(p));

        Pergunta result = perguntaService.getById("1");

        assertEquals("1", result.getId());
        verify(perguntaRepository, times(1)).findById("1");
    }

    @Test
    void deleteById_shouldCallRepository() {
        doNothing().when(perguntaRepository).deleteById("1");

        perguntaService.deleteById("1");

        verify(perguntaRepository, times(1)).deleteById("1");
    }
}
