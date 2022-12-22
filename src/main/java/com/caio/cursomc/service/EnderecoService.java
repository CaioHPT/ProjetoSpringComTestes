package com.caio.cursomc.service;

import com.caio.cursomc.model.Endereco;
import com.caio.cursomc.repository.EnderecoRepository;
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
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco findById(Long id){
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado ou não existe, ID:" + id, new Throwable("Tipo: " + Endereco.class.getName())));
    }

    public List<Endereco> findAll(){
        List<Endereco> enderecos = enderecoRepository.findAll();

        return enderecos;
    }

    public Page<Endereco> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return enderecoRepository.findAll(pageRequest);
    }

    public Endereco save(Endereco endereco) {
        endereco.setId(null);
        return enderecoRepository.save(endereco);
    }

    public Endereco update(Endereco endereco){
        this.findById(endereco.getId());
        return enderecoRepository.save(endereco);
    }

    public void delete(Long id){
        this.findById(id);
        try{
            enderecoRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Erro ao deletar, existem clientes e cidade associados a esse endereço, Id: " + id);
        }
    }
}
