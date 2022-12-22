package com.caio.cursomc.service;

import com.caio.cursomc.model.Estado;
import com.caio.cursomc.repository.EstadoRepository;
import com.caio.cursomc.service.exceptions.DataIntegrityException;
import com.caio.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado findById(Long id){
        Optional<Estado> estado = estadoRepository.findById(id);
        return estado.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado ou não existe, ID:" + id, new Throwable("Tipo: " + Estado.class.getName())));
    }

    public List<Estado> findAll(){
        List<Estado> estados = estadoRepository.findAll();

        return estados;
    }

    public Page<Estado> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return estadoRepository.findAll(pageRequest);
    }

    public Estado save(Estado estado) {
        estado.setId(null);
        return estadoRepository.save(estado);
    }

    public Estado update(Estado estado){
        this.findById(estado.getId());
        return estadoRepository.save(estado);
    }

    public void delete(Long id){
        this.findById(id);
        try{
            estadoRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Erro ao deletar, existem cidades associados a esse estado, Id: " + id);
        }
    }
}
