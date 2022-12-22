package com.caio.cursomc.repository;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Estado;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for cidade repository")
public class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    private static final String NAME_STATE = "São Paulo";
    private static final String NAME_CITY = "Vinhedo";

    @Test
    @DisplayName("Find all return list cidade when sucessful")
    void findAll_ReturnListCidade_WhenSucessful(){
        List<Cidade> cidades = cidadeRepository.findAll();

        Assertions.assertThat(cidades).isNotNull();
    }

    @Test
    @DisplayName("save cidade when sucessful")
    void save_PersistCidade_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Assertions.assertThat(cidadeSaved).isNotNull();

        Assertions.assertThat(cidadeSaved.getNome()).isNotNull().isEqualTo(cidade.getNome()).isEqualTo(NAME_CITY);

        Assertions.assertThat(cidadeSaved.getId()).isNotNull();

        Assertions.assertThat(cidadeSaved.getEstado()).isNotNull().isEqualTo(estadoSaved);

        Assertions.assertThat(cidadeSaved.getClass()).isEqualTo(Cidade.class);
    }

    @Test
    @DisplayName("update cidade when sucessful")
    void update_PersistCidade_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = new Cidade(1L, NAME_CITY, estadoSaved);
        Cidade cidadeUpdated = cidadeRepository.save(cidade);

        Assertions.assertThat(cidadeUpdated).isNotNull();

        Assertions.assertThat(cidadeUpdated.getNome()).isNotNull().isEqualTo(cidade.getNome()).isEqualTo(NAME_CITY);

        Assertions.assertThat(cidadeUpdated.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(cidadeUpdated.getEstado()).isNotNull().isEqualTo(estadoSaved);

        Assertions.assertThat(cidadeUpdated.getClass()).isEqualTo(Cidade.class);
    }

    @Test
    @DisplayName("delete cidade when sucessful")
    void delete_RemoveCidade_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        cidadeRepository.delete(cidadeSaved);

        Optional<Cidade> cidadeById = cidadeRepository.findById(cidadeSaved.getId());

        Assertions.assertThat(cidadeById).isEqualTo(Optional.empty());

        Assertions.assertThat(cidadeById.isPresent()).isFalse();
    }

    @Test
    @DisplayName("findById cidade when sucessful")
    void findById_FindCidade_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Optional<Cidade> cidadeById = cidadeRepository.findById(cidadeSaved.getId());

        Assertions.assertThat(cidadeById.get()).isNotNull().isIn(cidadeSaved);

        Assertions.assertThat(cidadeById.get()).isEqualTo(cidadeSaved);

        Assertions.assertThat(cidadeById.get().getNome()).isNotNull().isEqualTo(cidadeSaved.getNome()).isEqualTo(NAME_CITY);

        Assertions.assertThat(cidadeById.get().getId()).isNotNull().isEqualTo(cidadeSaved.getId());

        Assertions.assertThat(cidadeById.isPresent()).isTrue();

        Assertions.assertThat(cidadeById.get().getEstado()).isNotNull();

        Assertions.assertThat(cidadeById.get().getClass()).isEqualTo(Cidade.class);
    }

    @Test
    @DisplayName("findById cidade when not found")
    void findById_FindCidade_NotFound(){
        Optional<Cidade> cidadeById = cidadeRepository.findById(310L);

        Assertions.assertThat(cidadeById).isEqualTo(Optional.empty());

        Assertions.assertThat(cidadeById.isPresent()).isFalse();
    }


    private Cidade createdCidade(Estado estado){
        return new Cidade(null, NAME_CITY, estado);
    }

}