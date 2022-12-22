package com.caio.cursomc.service;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.repository.CidadeRepository;
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
public class CidadeServiceTest {

    private Estado estado;
    private Cidade cidade;

    @InjectMocks
    private CidadeService cidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    private static final String NAME_CITY = "Vinhedo";
    private static final String NAME_STATE = "São paulo";

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Cidade> cidades = Arrays.asList(cidade);

        PageImpl<Cidade> cidadePage = new PageImpl<Cidade>(Arrays.asList(cidade));

        BDDMockito.when(cidadeRepository.findAll()).thenReturn(cidades);

        BDDMockito.when(cidadeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(cidade));

        BDDMockito.when(cidadeRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(cidadePage);

        BDDMockito.when(cidadeRepository.save(ArgumentMatchers.any(Cidade.class)))
                .thenReturn(cidade);

        BDDMockito.doNothing().when(cidadeRepository).deleteById(1L);
    }

    @Test
    @DisplayName("List return of cidade when Sucessful")
    void list_ReturnCidades_WhenSucessful(){
        List<Cidade> cidades = cidadeService.findAll();

        Assertions.assertThat(cidades).isNotNull();

        Assertions.assertThat(cidades.get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(cidades.get(0).getNome()).isNotNull().isEqualTo(NAME_CITY);

        Assertions.assertThat(cidades.get(0).getEstado()).isNotNull().isEqualTo(estado);

        Assertions.assertThat(cidades.get(0).getClass()).isEqualTo(Cidade.class);

        Assertions.assertThat(cidades.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(cidadeService.findAll()).thenReturn(Collections.emptyList());

        List<Cidade> cidades = cidadeService.findAll();

        Assertions.assertThat(cidades).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(cidades.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("page return cidades when Sucessful")
    void page_ReturnCidades_WhenSucessful(){
        Page<Cidade> cidadePage = cidadeService.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(cidadePage).isNotNull();

        Assertions.assertThat(cidadePage.get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(cidadePage.get().count() == 1).isTrue();

        Assertions.assertThat(cidadePage.get().findFirst().get().getClass()).isEqualTo(Cidade.class);

        Assertions.assertThat(cidadePage.get().findFirst().get().getNome()).isEqualTo(NAME_CITY).isNotNull();

        Assertions.assertThat(cidadePage.get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(cidadePage.get().findFirst().get().getEstado()).isNotNull().isEqualTo(estado);

        Assertions.assertThat(cidadePage.toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return cidade by id when Sucessful")
    void returnCidadeById_WhenSucessful(){
        Cidade cidadeReturn = cidadeService.findById(1L);

        Assertions.assertThat(cidadeReturn).isNotNull();

        Assertions.assertThat(cidadeReturn.getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(cidadeReturn.getNome()).isEqualTo(NAME_CITY).isNotNull();

        Assertions.assertThat(cidadeReturn.getEstado()).isNotNull().isEqualTo(estado);

        Assertions.assertThat(Cidade.class).isEqualTo(cidadeReturn.getClass());
    }

    @Test
    @DisplayName("Return ObjectNotFound when cidade not found")
    void returnException_WhenNotFound(){
        BDDMockito.when(cidadeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ObjectNotFoundException.class)
                .isThrownBy(() -> cidadeService.findById(1L));
    }

    @Test
    @DisplayName("Save cidade when sucessful")
    void save_AddCidade_WhenSucessful(){
        Cidade retorno = cidadeService.save(new Cidade(null, NAME_CITY, estado));

        Assertions.assertThat(retorno.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(retorno.getNome()).isEqualTo(NAME_CITY).isNotNull();

        Assertions.assertThat(retorno.getClass()).isEqualTo(Cidade.class);

        Assertions.assertThat(retorno.getEstado()).isNotNull().isEqualTo(estado);

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Update cidade when sucessful")
    void update_ReplaceCidade_WhenSucessful(){
        Cidade retorno = cidadeService.update(cidade);

        Assertions.assertThatCode(() -> cidadeService.update(cidade))
                .doesNotThrowAnyException();

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete cidade when sucessful")
    void delete_RemoveCidade_WhenSucessful(){
        Assertions.assertThatCode(() -> cidadeService.delete(1L))
                .doesNotThrowAnyException();
    }

    private void startEntitys(){
        estado = new Estado(1L, NAME_STATE);
        cidade = new Cidade(1L, NAME_CITY, estado);
    }

}
