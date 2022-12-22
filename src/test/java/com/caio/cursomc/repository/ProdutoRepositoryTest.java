package com.caio.cursomc.repository;

import com.caio.cursomc.model.Categoria;
import com.caio.cursomc.model.Produto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for produto repository")
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final String NAME_CATEGORY = "ELETRONICOS";
    private static final String NAME_PRODUCT = "MOUSE";
    private static final Double PRICE_PRODUCT = 50.0;

    @Test
    @DisplayName("Find all return list produto when sucessful")
    void findAll_ReturnListProduto_WhenSucessful(){
        List<Produto> produtos = produtoRepository.findAll();

        Assertions.assertThat(produtos).isNotNull();
    }

    @Test
    @DisplayName("save produto when sucessful")
    void save_PersistProduto_WhenSucessful(){
        Categoria categoria = new Categoria(null, NAME_CATEGORY);
        Produto produto = createdProduto();

        categoria.getProdutos().add(produto);
        Categoria categoriaSaved = categoriaRepository.save(categoria);

        produto.getCategorias().add(categoriaSaved);
        Produto produtoSaved = produtoRepository.save(produto);

        Assertions.assertThat(produtoSaved).isNotNull();

        Assertions.assertThat(produtoSaved.getNome()).isNotNull().isEqualTo(produto.getNome()).isEqualTo(NAME_PRODUCT);

        Assertions.assertThat(produtoSaved.getId()).isNotNull();

        Assertions.assertThat(produtoSaved.getPreco()).isNotNull().isEqualTo(produto.getPreco()).isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtoSaved.getCategorias()).isNotNull().containsAll(Arrays.asList(categoriaSaved));

        Assertions.assertThat(produtoSaved.getPedidos()).isNotNull();

        Assertions.assertThat(produtoSaved.getItens()).isNotNull();

        Assertions.assertThat(produtoSaved.getClass()).isEqualTo(Produto.class);
    }

    @Test
    @DisplayName("update produto when sucessful")
    void update_PersistProduto_WhenSucessful(){
        Categoria categoria = new Categoria(null, NAME_CATEGORY);
        Produto produto = new Produto(1L, NAME_PRODUCT, PRICE_PRODUCT);

        categoria.getProdutos().add(produto);
        Categoria categoriaSaved = categoriaRepository.save(categoria);

        produto.getCategorias().add(categoriaSaved);
        Produto produtoUpdated = produtoRepository.save(produto);

        Assertions.assertThat(produtoUpdated).isNotNull();

        Assertions.assertThat(produtoUpdated.getNome()).isNotNull().isEqualTo(produto.getNome()).isEqualTo(NAME_PRODUCT);

        Assertions.assertThat(produtoUpdated.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(produtoUpdated.getPreco()).isNotNull().isEqualTo(produto.getPreco()).isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtoUpdated.getCategorias()).isNotNull().containsAll(Arrays.asList(categoriaSaved));

        Assertions.assertThat(produtoUpdated.getPedidos()).isNotNull();

        Assertions.assertThat(produtoUpdated.getItens()).isNotNull();

        Assertions.assertThat(produtoUpdated.getClass()).isEqualTo(Produto.class);

    }

    @Test
    @DisplayName("delete produto when sucessful")
    void delete_RemoveProduto_WhenSucessful(){
        Categoria categoria = new Categoria(null, NAME_CATEGORY);
        Produto produto = new Produto(1L, NAME_PRODUCT, PRICE_PRODUCT);

        categoria.getProdutos().add(produto);
        Categoria categoriaSaved = categoriaRepository.save(categoria);

        produto.getCategorias().add(categoriaSaved);
        Produto produtoSaved = produtoRepository.save(produto);

        produtoRepository.delete(produtoSaved);

        Optional<Produto> produtoById = produtoRepository.findById(produtoSaved.getId());

        Assertions.assertThat(produtoById).isEqualTo(Optional.empty());

        Assertions.assertThat(produtoById.isPresent()).isFalse();
    }

    @Test
    @DisplayName("findById produto when sucessful")
    void findById_FindProduto_WhenSucessful(){
        Categoria categoria = new Categoria(null, NAME_CATEGORY);
        Produto produto = new Produto(1L, NAME_PRODUCT, PRICE_PRODUCT);

        categoria.getProdutos().add(produto);
        Categoria categoriaSaved = categoriaRepository.save(categoria);

        produto.getCategorias().add(categoriaSaved);
        Produto produtoSaved = produtoRepository.save(produto);

        Optional<Produto> produtoById = produtoRepository.findById(produtoSaved.getId());

        Assertions.assertThat(produtoById.get()).isNotNull().isIn(produtoSaved);

        Assertions.assertThat(produtoById.get()).isEqualTo(produtoSaved);

        Assertions.assertThat(produtoById.get().getNome()).isNotNull().isEqualTo(produtoSaved.getNome()).isEqualTo(NAME_PRODUCT);

        Assertions.assertThat(produtoById.get().getId()).isNotNull().isEqualTo(produtoSaved.getId());

        Assertions.assertThat(produtoById.get().getPreco()).isNotNull().isEqualTo(produtoSaved.getPreco()).isEqualTo(PRICE_PRODUCT);

        Assertions.assertThat(produtoById.get().getItens()).isNotNull().isEqualTo(produtoSaved.getItens());

        Assertions.assertThat(produtoById.get().getCategorias()).isNotNull().containsAll(Arrays.asList(categoriaSaved));

        Assertions.assertThat(produtoById.isPresent()).isTrue();

        Assertions.assertThat(produtoById.get().getPedidos()).isNotNull();

        Assertions.assertThat(produtoById.get().getItens()).isNotNull();

        Assertions.assertThat(produtoById.get().getClass()).isEqualTo(Produto.class);


    }

    @Test
    @DisplayName("findById produto when not found")
    void findById_FindProduto_NotFound(){
        Optional<Produto> produtoById = produtoRepository.findById(310L);

        Assertions.assertThat(produtoById).isEqualTo(Optional.empty());

        Assertions.assertThat(produtoById.isPresent()).isFalse();
    }


    private Produto createdProduto(){
        return new Produto(null, NAME_PRODUCT, PRICE_PRODUCT);
    }

}