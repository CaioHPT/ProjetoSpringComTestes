package com.caio.cursomc.controller;

import com.caio.cursomc.DTO.CategoriaDTO;
import com.caio.cursomc.model.Categoria;
import com.caio.cursomc.service.CategoriaService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CategoriaControllerTest {

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService categoriaService;

    private static final String NAME_CATEGORY = "ELETRONICOS";

    @BeforeEach
    void setUp(){
        startEntitys();

        List<CategoriaDTO> categorias = Arrays.asList(categoriaDTO);

        PageImpl<Categoria> categoriaPage = new PageImpl<>(Arrays.asList(categoria));

        BDDMockito.when(categoriaService.findAll()).thenReturn(categorias);

        BDDMockito.when(categoriaService.findById(ArgumentMatchers.anyLong())).thenReturn(categoria);

        BDDMockito.when(categoriaService.findPage(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenReturn(categoriaPage);

        BDDMockito.when(categoriaService.save(ArgumentMatchers.any(CategoriaDTO.class))).thenReturn(categoria);

        BDDMockito.when(categoriaService.update(ArgumentMatchers.any(CategoriaDTO.class))).thenReturn(categoria);

        BDDMockito.doNothing().when(categoriaService).delete(1L);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    @DisplayName("List return of categorias when Sucessful")
    void list_ReturnCategorias_WhenSucessful(){
        ResponseEntity<List<CategoriaDTO>> categorias = categoriaController.findAll();

        Assertions.assertThat(categorias).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(categorias.getClass());

        Assertions.assertThat(categorias.getBody()).isNotNull();

        Assertions.assertThat(categorias.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(categorias.getBody().get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(categorias.getBody().get(0).getNome()).isNotNull().isEqualTo(NAME_CATEGORY);

        Assertions.assertThat(categorias.getBody().get(0).getClass()).isEqualTo(CategoriaDTO.class);

        Assertions.assertThat(categorias.getBody().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(categoriaService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CategoriaDTO>> categorias = categoriaController.findAll();

        Assertions.assertThat(categorias.getBody()).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(categorias.getBody().isEmpty()).isTrue();

        Assertions.assertThat(categorias).isNotNull();

        Assertions.assertThat(categorias.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(ResponseEntity.class).isEqualTo(categorias.getClass());

    }

    @Test
    @DisplayName("page return categorias when Sucessful")
    void page_ReturnCategorias_WhenSucessful(){
        ResponseEntity<Page<CategoriaDTO>> categoriaPage = categoriaController.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(categoriaPage).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(categoriaPage.getClass());

        Assertions.assertThat(categoriaPage.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(categoriaPage.getBody()).isNotNull();

        Assertions.assertThat(categoriaPage.getBody().get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(categoriaPage.getBody().get().count() == 1).isTrue();

        Assertions.assertThat(categoriaPage.getBody().get().findFirst().get().getClass()).isEqualTo(CategoriaDTO.class);

        Assertions.assertThat(categoriaPage.getBody().get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(categoriaPage.getBody().get().findFirst().get().getNome()).isEqualTo(NAME_CATEGORY).isNotNull();

        Assertions.assertThat(categoriaPage.getBody().toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return categoria by id when Sucessful")
    void returnCategoriaById_WhenSucessful(){
        ResponseEntity<Categoria> categoriaReturn = categoriaController.findById(1L);

        Assertions.assertThat(categoriaReturn).isNotNull();

        Assertions.assertThat(categoriaReturn.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(categoriaReturn.getBody()).isNotNull();

        Assertions.assertThat(categoriaReturn.getBody().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(categoriaReturn.getBody().getNome()).isEqualTo(NAME_CATEGORY).isNotNull();

        Assertions.assertThat(categoriaReturn.getBody().getProdutos()).isNotNull();

        Assertions.assertThat(Categoria.class).isEqualTo(categoriaReturn.getBody().getClass());

        Assertions.assertThat(ResponseEntity.class).isEqualTo(categoriaReturn.getClass());

    }
    
    @Test
    @DisplayName("Save categoria when sucessful")
    void save_AddCategoria_WhenSucessful(){
        ResponseEntity<Void> retorno = categoriaController.save(new CategoriaDTO(null, NAME_CATEGORY));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getHeaders().get("Location")).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("Update categoria when sucessful")
    void update_ReplaceCategoria_WhenSucessful(){
        ResponseEntity<Void> retorno = categoriaController.update(1L, new CategoriaDTO(null, NAME_CATEGORY));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete categoria when sucessful")
    void delete_RemoveCategoria_WhenSucessful(){
        ResponseEntity<Void> retorno = categoriaController.delete(1L);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(retorno).isNotNull();
    }

    private void startEntitys(){
        categoria = new Categoria(1L, NAME_CATEGORY);
        categoriaDTO = new CategoriaDTO(1L, NAME_CATEGORY);
    }
}
