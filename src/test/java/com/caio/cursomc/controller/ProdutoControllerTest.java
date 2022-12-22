package com.caio.cursomc.controller;

import com.caio.cursomc.DTO.ProdutoDTO;
import com.caio.cursomc.model.*;
import com.caio.cursomc.service.ProdutoService;
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
public class ProdutoControllerTest {

    private Categoria categoria;
    private Produto produto;

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private static final String NAME_CATEGORY = "ELETRONICOS";
    private static final String NAME_PRODUCT = "MOUSE";
    private static final Double PRICE_PRODUCT = 50.0;

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Produto> produtos = Arrays.asList(produto);

        PageImpl<Produto> produtoPage = new PageImpl<>(Arrays.asList(produto));

        BDDMockito.when(produtoService.findAll()).thenReturn(produtos);

        BDDMockito.when(produtoService.findById(ArgumentMatchers.anyLong())).thenReturn(produto);

        BDDMockito.when(produtoService.findPage(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenReturn(produtoPage);

        BDDMockito.when(produtoService.search(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.<Long>anyList(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())).thenReturn(produtoPage);

        BDDMockito.when(produtoService.save(ArgumentMatchers.any(Produto.class))).thenReturn(produto);

        BDDMockito.when(produtoService.update(ArgumentMatchers.any(Produto.class))).thenReturn(produto);

        BDDMockito.doNothing().when(produtoService).delete(1L);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    @DisplayName("List return of produtos when Sucessful")
    void list_ReturnProdutos_WhenSucessful(){
        ResponseEntity<List<Produto>> produtos = produtoController.findAll();

        Assertions.assertThat(produtos).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(produtos.getClass());

        Assertions.assertThat(produtos.getBody()).isNotNull();

        Assertions.assertThat(produtos.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(produtos.getBody().get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(produtos.getBody().get(0).getNome()).isNotNull().isEqualTo(NAME_PRODUCT);

        Assertions.assertThat(produtos.getBody().get(0).getPreco()).isNotNull().isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtos.getBody().get(0).getCategorias()).isNotNull().isNotEmpty();

        Assertions.assertThat(produtos.getBody().get(0).getItens()).isNotNull();

        Assertions.assertThat(produtos.getBody().get(0).getPedidos()).isNotNull();

        Assertions.assertThat(produtos.getBody().get(0).getClass()).isEqualTo(Produto.class);

        Assertions.assertThat(produtos.getBody().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(produtoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Produto>> produtos = produtoController.findAll();

        Assertions.assertThat(produtos.getBody()).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(produtos.getBody().isEmpty()).isTrue();

        Assertions.assertThat(produtos).isNotNull();

        Assertions.assertThat(produtos.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(ResponseEntity.class).isEqualTo(produtos.getClass());

    }

    @Test
    @DisplayName("page return produtos when Sucessful")
    void page_ReturnProdutos_WhenSucessful(){
        ResponseEntity<Page<Produto>> produtoPage = produtoController.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(produtoPage).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(produtoPage.getClass());

        Assertions.assertThat(produtoPage.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(produtoPage.getBody()).isNotNull();

        Assertions.assertThat(produtoPage.getBody().get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(produtoPage.getBody().get().count() == 1).isTrue();

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getClass()).isEqualTo(Produto.class);

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getNome()).isNotNull().isEqualTo(NAME_PRODUCT);

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getPreco()).isNotNull().isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getCategorias()).isNotNull().isNotEmpty();

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getItens()).isNotNull();

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getPedidos()).isNotNull();

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(produtoPage.getBody().toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Search return of produtos when Sucessful")
    void page_ReturnProdutos_WhenSucessfulSearch(){
        ResponseEntity<Page<ProdutoDTO>> produtoPage = produtoController.search(
                NAME_PRODUCT, "1", 1, 1, "nome", "ASC"
        );


        Assertions.assertThat(produtoPage).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(produtoPage.getClass());

        Assertions.assertThat(produtoPage.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(produtoPage.getBody()).isNotNull();

        Assertions.assertThat(produtoPage.getBody().get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(produtoPage.getBody().get().count() == 1).isTrue();

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getClass()).isEqualTo(ProdutoDTO.class);

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getNome()).isNotNull().isEqualTo(NAME_PRODUCT);

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getPreco()).isNotNull().isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtoPage.getBody().get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(produtoPage.getBody().toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return produto by id when Sucessful")
    void returnProdutoById_WhenSucessful(){
        ResponseEntity<Produto> produtoReturn = produtoController.findById(1L);

        Assertions.assertThat(produtoReturn).isNotNull();

        Assertions.assertThat(produtoReturn.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(produtoReturn.getBody()).isNotNull();

        Assertions.assertThat(produtoReturn.getBody().getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(produtoReturn.getBody().getNome()).isNotNull().isEqualTo(NAME_PRODUCT);

        Assertions.assertThat(produtoReturn.getBody().getPreco()).isNotNull().isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtoReturn.getBody().getCategorias()).isNotNull().isNotEmpty();

        Assertions.assertThat(produtoReturn.getBody().getItens()).isNotNull();

        Assertions.assertThat(produtoReturn.getBody().getPedidos()).isNotNull();

        Assertions.assertThat(Produto.class).isEqualTo(produtoReturn.getBody().getClass());

        Assertions.assertThat(ResponseEntity.class).isEqualTo(produtoReturn.getClass());

    }

    @Test
    @DisplayName("Save produto when sucessful")
    void save_AddProduto_WhenSucessful(){
        Produto produtoSaved = new Produto(null, NAME_PRODUCT, PRICE_PRODUCT);

        produtoSaved.getCategorias().add(categoria);

        ResponseEntity<Void> retorno = produtoController.save(produtoSaved);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getHeaders().get("Location")).isNotNull().isNotEmpty();

    }

    @Test
    @DisplayName("Update Produto when sucessful")
    void update_ReplaceProduto_WhenSucessful(){
        Produto produtoUpdated = new Produto(null, NAME_PRODUCT, PRICE_PRODUCT);

        produtoUpdated.getCategorias().add(categoria);

        ResponseEntity<Void> retorno = produtoController.update(1L, produtoUpdated);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete produto when sucessful")
    void delete_RemoveProduto_WhenSucessful(){
        ResponseEntity<Void> retorno = produtoController.delete(1L);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(retorno).isNotNull();
    }

    private void startEntitys(){
        categoria = new Categoria(1L, NAME_CATEGORY);
        produto = new Produto(1L, NAME_PRODUCT, PRICE_PRODUCT);

        produto.getCategorias().add(categoria);
    }
}
