package com.caio.cursomc.repository;


import com.caio.cursomc.model.Categoria;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for categoria repository")
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final String NAMA_CATEGORY = "Escritorio";

    @Test
    @DisplayName("Find all return list categorias when sucessful")
    void findAll_ReturnListCategoria_WhenSucessful(){
        List<Categoria> categorias = categoriaRepository.findAll();

        Assertions.assertThat(categorias).isNotNull();
    }

    @Test
    @DisplayName("save categoria when sucessful")
    void save_PersistCategoria_WhenSucessful(){
        Categoria categoria = createdCategoria();
        Categoria categoriaSaved = categoriaRepository.save(categoria);

        Assertions.assertThat(categoriaSaved).isNotNull();

        Assertions.assertThat(categoriaSaved.getNome()).isNotNull().isEqualTo(categoria.getNome()).isEqualTo(NAMA_CATEGORY);

        Assertions.assertThat(categoriaSaved.getId()).isNotNull();

        Assertions.assertThat(categoriaSaved.getClass()).isEqualTo(Categoria.class);

        Assertions.assertThat(categoriaSaved.getProdutos()).isNotNull();

    }

    @Test
    @DisplayName("update categoria when sucessful")
    void update_PersistCategoria_WhenSucessful(){
        Categoria categoria = new Categoria(1L, NAMA_CATEGORY);
        Categoria categoriaUpdated = categoriaRepository.save(categoria);

        Assertions.assertThat(categoriaUpdated).isNotNull();

        Assertions.assertThat(categoriaUpdated.getNome()).isNotNull().isEqualTo(categoria.getNome()).isEqualTo(NAMA_CATEGORY);

        Assertions.assertThat(categoriaUpdated.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(categoriaUpdated.getClass()).isEqualTo(Categoria.class);

        Assertions.assertThat(categoriaUpdated.getProdutos()).isNotNull();
    }

    @Test
    @DisplayName("delete categoria when sucessful")
    void delete_RemoveCategoria_WhenSucessful(){
        Categoria categoria = createdCategoria();
        Categoria categoriaSaved = categoriaRepository.save(categoria);

        categoriaRepository.delete(categoriaSaved);

        Optional<Categoria> categoriaById = categoriaRepository.findById(categoriaSaved.getId());

        Assertions.assertThat(categoriaById).isEqualTo(Optional.empty());

        Assertions.assertThat(categoriaById.isPresent()).isFalse();
    }

    @Test
    @DisplayName("findById categoria when sucessful")
    void findById_FindCategoria_WhenSucessful(){
        Categoria categoria = createdCategoria();
        Categoria categoriaSaved = categoriaRepository.save(categoria);

        Optional<Categoria> categoriaById = categoriaRepository.findById(categoriaSaved.getId());

        Assertions.assertThat(categoriaById.get()).isNotNull().isIn(categoriaSaved);

        Assertions.assertThat(categoriaById.get()).isEqualTo(categoriaSaved);

        Assertions.assertThat(categoriaById.get().getNome()).isNotNull().isEqualTo(categoriaSaved.getNome()).isEqualTo(NAMA_CATEGORY);

        Assertions.assertThat(categoriaById.get().getId()).isNotNull().isEqualTo(categoriaSaved.getId());

        Assertions.assertThat(categoriaById.get().getClass()).isEqualTo(Categoria.class);

        Assertions.assertThat(categoriaById.get().getProdutos()).isNotNull();

        Assertions.assertThat(categoriaById.isPresent()).isTrue();
    }

    @Test
    @DisplayName("findById categoria when not found")
    void findById_FindCategoria_NotFound(){
        Optional<Categoria> categoriaById = categoriaRepository.findById(310L);

        Assertions.assertThat(categoriaById).isEqualTo(Optional.empty());

        Assertions.assertThat(categoriaById.isPresent()).isFalse();
    }


    private Categoria createdCategoria(){
        return new Categoria(null, NAMA_CATEGORY);
    }

}