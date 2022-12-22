package com.caio.cursomc.service;

import com.caio.cursomc.DTO.CategoriaDTO;
import com.caio.cursomc.model.Categoria;
import com.caio.cursomc.repository.CategoriaRepository;
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
public class CategoriaServiceTest {

    private Categoria categoria;

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    private static final String NAME_CATEGORY = "ELETRONICOS";

    @BeforeEach
    void setUp(){
        startEntity();

        List<Categoria> categorias = Arrays.asList(categoria);

        PageImpl<Categoria> categoriaPage = new PageImpl<Categoria>(Arrays.asList(categoria));

        BDDMockito.when(categoriaRepository.findAll()).thenReturn(categorias);

        BDDMockito.when(categoriaRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(categoria));

        BDDMockito.when(categoriaRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(categoriaPage);

        BDDMockito.when(categoriaRepository.save(ArgumentMatchers.any(Categoria.class))).thenReturn(categoria);

        BDDMockito.doNothing().when(categoriaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("List return of categorias when Sucessful")
    void list_ReturnCategorias_WhenSucessful(){
        List<CategoriaDTO> categorias = categoriaService.findAll();

        Assertions.assertThat(categorias).isNotNull();

        Assertions.assertThat(categorias.get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(categorias.get(0).getNome()).isNotNull().isEqualTo(NAME_CATEGORY);

        Assertions.assertThat(categorias.get(0).getClass()).isEqualTo(CategoriaDTO.class);

        Assertions.assertThat(categorias.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(categoriaService.findAll()).thenReturn(Collections.emptyList());

        List<CategoriaDTO> categoriaDTOS = categoriaService.findAll();

        Assertions.assertThat(categoriaDTOS).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(categoriaDTOS.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("page return categorias when Sucessful")
    void page_ReturnCategorias_WhenSucessful(){
        Page<Categoria> categoriaPage = categoriaService.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(categoriaPage).isNotNull();

        Assertions.assertThat(categoriaPage.get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(categoriaPage.get().count() == 1).isTrue();

        Assertions.assertThat(categoriaPage.get().findFirst().get().getClass()).isEqualTo(Categoria.class);

        Assertions.assertThat(categoriaPage.get().findFirst().get().getNome()).isEqualTo(NAME_CATEGORY).isNotNull();

        Assertions.assertThat(categoriaPage.get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(categoriaPage.get().findFirst().get().getProdutos()).isNotNull();

        Assertions.assertThat(categoriaPage.toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return categoria by id when Sucessful")
    void returnCategoriaById_WhenSucessful(){
        Categoria categoriaReturn = categoriaService.findById(1L);

        Assertions.assertThat(categoriaReturn).isNotNull();

        Assertions.assertThat(categoriaReturn.getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(categoriaReturn.getNome()).isEqualTo(NAME_CATEGORY).isNotNull();

        Assertions.assertThat(categoriaReturn.getProdutos()).isNotNull();

        Assertions.assertThat(Categoria.class).isEqualTo(categoriaReturn.getClass());
    }

    @Test
    @DisplayName("Return ObjectNotFound when categoria not found")
    void returnException_WhenNotFound(){
        BDDMockito.when(categoriaRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ObjectNotFoundException.class)
                .isThrownBy(() -> categoriaService.findById(1L));
    }

    @Test
    @DisplayName("Save categoria when sucessful")
    void save_AddCategoria_WhenSucessful(){
        Categoria retorno = categoriaService.save(new CategoriaDTO(null, NAME_CATEGORY));

        Assertions.assertThat(retorno.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(retorno.getNome()).isEqualTo(NAME_CATEGORY).isNotNull();

        Assertions.assertThat(retorno.getProdutos()).isNotNull();

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(Categoria.class).isEqualTo(retorno.getClass());
    }

    @Test
    @DisplayName("Update categoria when sucessful")
    void update_ReplaceCategoria_WhenSucessful(){
        Categoria retorno = categoriaService.update(new CategoriaDTO(1L, NAME_CATEGORY));

        Assertions.assertThatCode(() -> categoriaService.update(new CategoriaDTO(1L, NAME_CATEGORY)))
                .doesNotThrowAnyException();

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete categoria when sucessful")
    void delete_RemoveCategoria_WhenSucessful(){
        Assertions.assertThatCode(() -> categoriaService.delete(1L))
                .doesNotThrowAnyException();
    }

    private void startEntity(){
        categoria = new Categoria(1L, NAME_CATEGORY);
    }
}
