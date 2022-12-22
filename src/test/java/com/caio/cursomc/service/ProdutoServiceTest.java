package com.caio.cursomc.service;

import com.caio.cursomc.model.Categoria;
import com.caio.cursomc.model.Produto;
import com.caio.cursomc.repository.CategoriaRepository;
import com.caio.cursomc.repository.ProdutoRepository;
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
public class ProdutoServiceTest {

    private Categoria categoria;
    private Produto produto;

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    private static final String NAME_CATEGORY = "ELETRONICOS";
    private static final String NAME_PRODUCT = "MOUSE";
    private static final Double PRICE_PRODUCT = 50.0;

    @BeforeEach
    void setUp(){
        startEntitys();

        produto.getCategorias().add(categoria);

        List<Produto> produtos = Arrays.asList(produto);

        PageImpl<Produto> produtoPage = new PageImpl<>(Arrays.asList(produto));

        BDDMockito.when(produtoRepository.findAll()).thenReturn(produtos);

        BDDMockito.when(produtoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(produto));

        BDDMockito.when(produtoRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(produtoPage);

        BDDMockito.when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenReturn(produto);

        BDDMockito.when(produtoRepository.
                findDistinctByNomeContainingAndCategoriasIn(ArgumentMatchers.anyString(), ArgumentMatchers.<Categoria>anyList(), ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(produtoPage);

        BDDMockito.doNothing().when(produtoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Search return of produtos when Sucessful")
    void page_ReturnProdutos_WhenSucessfulSearch(){
        Page<Produto> produtoPage = produtoService.search(NAME_PRODUCT,
                Arrays.asList(1L),
                2,
                1,
                "nome",
                "DESC");

        Assertions.assertThat(produtoPage).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(produtoPage.get().count() == 1).isTrue();

        Assertions.assertThat(produtoPage.get().findFirst().get().getClass()).isEqualTo(Produto.class);

        Assertions.assertThat(produtoPage.get().findFirst().get().getNome()).isEqualTo(NAME_PRODUCT).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().get().getPreco()).isEqualTo(PRICE_PRODUCT).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().get().getCategorias()).isNotNull().isNotEmpty();

        Assertions.assertThat(produtoPage.get().findFirst().get().getItens()).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().get().getPedidos()).isNotNull();

        Assertions.assertThat(produtoPage.toList()).isNotEmpty();
    }

    @Test
    @DisplayName("List return of produtos when is Sucessful")
    void list_ReturnProdutos_WhenSucessful(){
        List<Produto> produtos = produtoService.findAll();

        Assertions.assertThat(produtos).isNotNull();

        Assertions.assertThat(produtos.get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(produtos.get(0).getNome()).isNotNull().isEqualTo(NAME_PRODUCT);

        Assertions.assertThat(produtos.get(0).getPreco()).isNotNull().isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtos.get(0).getCategorias()).isNotEmpty().isNotNull();

        Assertions.assertThat(produtos.get(0).getItens()).isNotNull();

        Assertions.assertThat(produtos.get(0).getPedidos()).isNotNull();

        Assertions.assertThat(produtos.get(0).getClass()).isEqualTo(Produto.class);

        Assertions.assertThat(produtos.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(produtoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Produto> produtos = produtoService.findAll();

        Assertions.assertThat(produtos).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(produtos.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("page return produtos when Sucessful")
    void page_ReturnProdutos_WhenSucessful(){
        Page<Produto> produtoPage = produtoService.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(produtoPage).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(produtoPage.get().count() == 1).isTrue();

        Assertions.assertThat(produtoPage.get().findFirst().get().getClass()).isEqualTo(Produto.class);

        Assertions.assertThat(produtoPage.get().findFirst().get().getNome()).isEqualTo(NAME_PRODUCT).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().get().getPreco()).isEqualTo(PRICE_PRODUCT).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().get().getCategorias()).isNotNull().isNotEmpty();

        Assertions.assertThat(produtoPage.get().findFirst().get().getItens()).isNotNull();

        Assertions.assertThat(produtoPage.get().findFirst().get().getPedidos()).isNotNull();

        Assertions.assertThat(produtoPage.toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return produto by id when Sucessful")
    void returnProdutoById_WhenSucessful(){
        Produto produtoReturn = produtoService.findById(1L);

        Assertions.assertThat(produtoReturn).isNotNull();

        Assertions.assertThat(produtoReturn.getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(produtoReturn.getNome()).isEqualTo(NAME_PRODUCT).isNotNull();

        Assertions.assertThat(produtoReturn.getPreco()).isNotNull().isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtoReturn.getCategorias()).isNotEmpty().isNotNull();

        Assertions.assertThat(produtoReturn.getItens()).isNotNull();

        Assertions.assertThat(produtoReturn.getPedidos()).isNotNull();

        Assertions.assertThat(Produto.class).isEqualTo(produtoReturn.getClass());
    }

    @Test
    @DisplayName("Return ObjectNotFound when produto not found")
    void returnException_WhenNotFound(){
        BDDMockito.when(produtoRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ObjectNotFoundException.class)
                .isThrownBy(() -> produtoService.findById(1L));
    }

    @Test
    @DisplayName("Save produto when sucessful")
    void save_AddProduto_WhenSucessful(){
        Produto produto = new Produto(null, NAME_PRODUCT, PRICE_PRODUCT);

        produto.getCategorias().add(categoria);

        Produto retorno = produtoService.save(produto);

        Assertions.assertThat(retorno.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(retorno.getNome()).isEqualTo(NAME_PRODUCT).isNotNull();

        Assertions.assertThat(retorno.getPreco()).isNotNull().isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(retorno.getCategorias()).isNotEmpty().isNotNull();

        Assertions.assertThat(retorno.getItens()).isNotNull();

        Assertions.assertThat(retorno.getPedidos()).isNotNull();

        Assertions.assertThat(Produto.class).isEqualTo(retorno.getClass());

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Update produto when sucessful")
    void update_ReplaceProduto_WhenSucessful(){
        Produto retorno = produtoService.update(produto);

        Assertions.assertThatCode(() -> produtoService.update(produto))
                .doesNotThrowAnyException();

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete produto when sucessful")
    void delete_RemoveProduto_WhenSucessful(){
        Assertions.assertThatCode(() -> produtoService.delete(1L))
                .doesNotThrowAnyException();
    }

    private void startEntitys(){
        categoria = new Categoria(1L, NAME_CATEGORY);
        produto = new Produto(1L, NAME_PRODUCT, PRICE_PRODUCT);

        produto.getCategorias().add(categoria);
    }

}
