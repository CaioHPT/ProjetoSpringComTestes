package com.caio.cursomc.service;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.repository.CidadeRepository;
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
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade findById(Long id){
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        return cidade.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado ou não existe, ID:" + id, new Throwable("Tipo: " + Cidade.class.getName())));
    }

    public List<Cidade> findAll(){
        List<Cidade> categorias = cidadeRepository.findAll();

        return categorias;
    }

    public Page<Cidade> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return cidadeRepository.findAll(pageRequest);
    }

    public Cidade save(Cidade cidade) {
        cidade.setId(null);
        return cidadeRepository.save(cidade);
    }

    public Cidade update(Cidade cidade){
        this.findById(cidade.getId());
        return cidadeRepository.save(cidade);
    }

    public void delete(Long id){
        this.findById(id);
        try{
            cidadeRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Erro ao deletar, exite estado associado a esta cidade, Id: " + id);
        }
    }

}
