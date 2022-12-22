package com.caio.cursomc.service;

import com.caio.cursomc.DTO.CategoriaDTO;
import com.caio.cursomc.model.Categoria;
import com.caio.cursomc.repository.CategoriaRepository;
import com.caio.cursomc.service.exceptions.DataIntegrityException;
import com.caio.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(Long id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado ou não existe, ID:" + id, new Throwable("Tipo: " + Categoria.class.getName())));
    }

    public List<CategoriaDTO> findAll(){
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> categoriaDTOS = categorias.stream().map(item -> new CategoriaDTO(item.getId(), item.getNome())).collect(Collectors.toList());

        return categoriaDTOS;
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria save(CategoriaDTO categoriaDTO) {
        Categoria categoria = this.fromDTO(categoriaDTO);
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(CategoriaDTO categoria){
        this.findById(categoria.getId());
        return categoriaRepository.save(this.fromDTO(categoria));
    }

    public void delete(Long id){
        this.findById(id);
        try{
            categoriaRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Erro ao deletar, existem produtos associados a esta categoria, Id: " + id);
        }
    }

    private Categoria fromDTO(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }
}
