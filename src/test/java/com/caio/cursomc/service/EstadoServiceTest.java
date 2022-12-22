package com.caio.cursomc.service;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.repository.EstadoRepository;
import com.caio.cursomc.service.exceptions.ObjectNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class EstadoServiceTest {

    private Estado estado;
    private Cidade cidade;

    @InjectMocks
    private EstadoService estadoService;

    @Mock
    private EstadoRepository estadoRepository;

    private static final String NAME_STATE = "São paulo";
    private static final String NAME_CITY = "Vinhedo";

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Estado> estados = Arrays.asList(estado);

        PageImpl<Estado> estadoPage = new PageImpl<>(Arrays.asList(estado));

        BDDMockito.when(estadoRepository.findAll()).thenReturn(estados);

        BDDMockito.when(estadoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(estado));

        BDDMockito.when(estadoRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(estadoPage);

        BDDMockito.when(estadoRepository.save(ArgumentMatchers.any(Estado.class))).thenReturn(estado);

        BDDMockito.doNothing().when(estadoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("List return of estados when Sucessful")
    void list_ReturnEstados_WhenSucessful(){
        List<Estado> estados = estadoService.findAll();

        Assertions.assertThat(estados).isNotNull();

        Assertions.assertThat(estados.get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(estados.get(0).getNome()).isNotNull().isEqualTo(NAME_STATE);

        Assertions.assertThat(estados.get(0).getCidades()).isNotNull().isNotEmpty().isEqualTo(Arrays.asList(cidade));

        Assertions.assertThat(estados.get(0).getClass()).isNotNull().isEqualTo(Estado.class);

        Assertions.assertThat(estados.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(estadoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Estado> estados = estadoService.findAll();

        Assertions.assertThat(estados).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(estados.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("page return estados when Sucessful")
    void page_ReturnEstados_WhenSucessful(){
        Page<Estado> estadoPage = estadoService.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(estadoPage).isNotNull();

        Assertions.assertThat(estadoPage.get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(estadoPage.get().count() == 1).isTrue();

        Assertions.assertThat(estadoPage.get().findFirst().get().getClass()).isEqualTo(Estado.class);

        Assertions.assertThat(estadoPage.get().findFirst().get().getNome()).isEqualTo(NAME_STATE).isNotNull();

        Assertions.assertThat(estadoPage.get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(estadoPage.get().findFirst().get().getCidades()).isNotNull().isNotEmpty().isEqualTo(Arrays.asList(cidade));

        Assertions.assertThat(estadoPage.toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return estado by id when Sucessful")
    void returnEstadoById_WhenSucessful(){
        Estado estadoReturn = estadoService.findById(1L);

        Assertions.assertThat(estadoReturn).isNotNull();

        Assertions.assertThat(estadoReturn.getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(estadoReturn.getNome()).isEqualTo(NAME_STATE).isNotNull();

        Assertions.assertThat(estadoReturn.getCidades()).isNotEmpty().isNotNull().isEqualTo(Arrays.asList(cidade));

        Assertions.assertThat(Estado.class).isEqualTo(estadoReturn.getClass());
    }

    @Test
    @DisplayName("Return ObjectNotFound when estado not found")
    void returnException_WhenNotFound(){
        BDDMockito.when(estadoRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ObjectNotFoundException.class)
                .isThrownBy(() -> estadoService.findById(1L));
    }

    @Test
    @DisplayName("Save estado when sucessful")
    void save_AddEstado_WhenSucessful(){
        Estado estadoSaved = new Estado(null, NAME_STATE);

        estadoSaved.setCidades(Arrays.asList(cidade));

        Estado retorno = estadoService.save(estadoSaved);

        Assertions.assertThat(retorno.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(retorno.getNome()).isEqualTo(NAME_STATE).isNotNull();

        Assertions.assertThat(retorno.getCidades()).isNotEmpty().isNotNull().isEqualTo(Arrays.asList(cidade));

        Assertions.assertThat(retorno.getClass()).isEqualTo(Estado.class);

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Update estado when sucessful")
    void update_ReplaceEstado_WhenSucessful(){
        Estado retorno = estadoService.update(estado);

        Assertions.assertThatCode(() -> estadoService.update(estado))
                .doesNotThrowAnyException();

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete estado when sucessful")
    void delete_RemoveEstado_WhenSucessful(){
        Assertions.assertThatCode(() -> estadoService.delete(1L))
                .doesNotThrowAnyException();
    }

    private void startEntitys(){
        estado = new Estado(1L, NAME_STATE);
        cidade = new Cidade(1L, NAME_CITY, estado);

        estado.getCidades().add(cidade);
    }
}
