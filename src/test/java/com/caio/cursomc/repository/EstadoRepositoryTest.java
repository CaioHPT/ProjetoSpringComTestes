package com.caio.cursomc.repository;

import com.caio.cursomc.model.Estado;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for estado repository")
public class EstadoRepositoryTest {

    @Autowired
    private EstadoRepository estadoRepository;

    private static final String NAME_STATE = "São paulo";


    @Test
    @DisplayName("Find all return list estados when sucessful")
    void findAll_ReturnListEstados_WhenSucessful(){
        List<Estado> estados = estadoRepository.findAll();

        Assertions.assertThat(estados).isNotNull();
    }

    @Test
    @DisplayName("save estado when sucessful")
    void save_PersistEstado_WhenSucessful(){
        Estado estado = createdEstado();
        Estado estadoSaved = estadoRepository.save(estado);

        Assertions.assertThat(estadoSaved).isNotNull();

        Assertions.assertThat(estadoSaved.getNome()).isNotNull().isEqualTo(estado.getNome()).isEqualTo(NAME_STATE);

        Assertions.assertThat(estadoSaved.getCidades()).isNotNull();

        Assertions.assertThat(estadoSaved.getId()).isNotNull();

    }

    @Test
    @DisplayName("update estado when sucessful")
    void update_PersistEstado_WhenSucessful(){
        Estado estado = new Estado(1L, NAME_STATE);
        Estado estadoUpdated = estadoRepository.save(estado);

        Assertions.assertThat(estadoUpdated).isNotNull();

        Assertions.assertThat(estadoUpdated.getNome()).isNotNull().isEqualTo(estado.getNome()).isEqualTo(estado.getNome()).isEqualTo(NAME_STATE);

        Assertions.assertThat(estadoUpdated.getCidades()).isNotNull();

        Assertions.assertThat(estadoUpdated.getId()).isNotNull().isEqualTo(1L);

    }

    @Test
    @DisplayName("delete estado when sucessful")
    void delete_RemoveEstado_WhenSucessful(){
        Estado estado = createdEstado();
        Estado estadoSaved = estadoRepository.save(estado);

        estadoRepository.delete(estadoSaved);

        Optional<Estado> estadoById = estadoRepository.findById(estadoSaved.getId());

        Assertions.assertThat(estadoById).isEqualTo(Optional.empty());

        Assertions.assertThat(estadoById.isPresent()).isFalse();
    }

    @Test
    @DisplayName("findById estado when sucessful")
    void findById_FindEstado_WhenSucessful(){
        Estado estado = createdEstado();
        Estado estadoSaved = estadoRepository.save(estado);

        Optional<Estado> estadoById = estadoRepository.findById(estadoSaved.getId());

        Assertions.assertThat(estadoById.get()).isNotNull().isIn(estadoSaved);

        Assertions.assertThat(estadoById.get()).isEqualTo(estadoSaved);

        Assertions.assertThat(estadoById.get().getNome()).isNotNull().isEqualTo(estadoSaved.getNome()).isEqualTo(NAME_STATE);

        Assertions.assertThat(estadoById.get().getCidades()).isNotNull();

        Assertions.assertThat(estadoById.get().getId()).isNotNull().isEqualTo(estadoSaved.getId());

        Assertions.assertThat(estadoById.isPresent()).isTrue();
    }

    @Test
    @DisplayName("findById estado when not found")
    void findById_FindEstado_NotFound(){
        Optional<Estado> estadoById = estadoRepository.findById(310L);

        Assertions.assertThat(estadoById).isEqualTo(Optional.empty());

        Assertions.assertThat(estadoById.isPresent()).isFalse();
    }


    private Estado createdEstado(){
        return new Estado(null, NAME_STATE);
    }
}