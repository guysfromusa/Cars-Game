package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovementsHistoryServiceTest {

    @Mock
    private MovementsHistoryRepository repository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private MovementsHistoryService movementsHistoryService;

    @Test
    public void shouldReturnEmptyList(){
        //given
        when(repository.findMovements(anyList(), anyList(), of(anyInt()))).thenReturn(emptyList());

        //when
        List<MovementHistory> result = movementsHistoryService.findMovementsHistory(emptyList(), emptyList(), of(2));

        //then
        assertThat(result).isEmpty();
    }
}