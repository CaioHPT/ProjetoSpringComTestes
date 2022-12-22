package com.caio.cursomc.service;

import com.caio.cursomc.model.Categoria;
import com.caio.cursomc.model.Produto;
import com.caio.cursomc.repository.CategoriaRepository;
import com.caio.cursomc.repository.ProdutoRepository;
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
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findById(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado ou não existe, ID:" + id, new Throwable("Tipo: " + Produto.class.getName())));
    }

    public Page<Produto> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }

    public List<Produto> findAll(){
        List<Produto> produtos = produtoRepository.findAll();

        return produtos;
    }

    public Page<Produto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return produtoRepository.findAll(pageRequest);
    }

    public Produto save(Produto produto) {
        produto.setId(null);
        return produtoRepository.save(produto);
    }

    public Produto update(Produto produto){
        this.findById(produto.getId());
        return produtoRepository.save(produto);
    }

    public void delete(Long id){
        this.findById(id);
        try{
            produtoRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Erro ao deletar, existem categorias associados a esse produto, Id: " + id);
        }
    }

}
