package com.caio.cursomc.controller;

import com.caio.cursomc.DTO.ProdutoDTO;
import com.caio.cursomc.controller.utils.URL;
import com.caio.cursomc.model.Produto;
import com.caio.cursomc.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produto")
@Api("Product REST API")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{id}")
    @ApiOperation("Returns product by id if found, otherwise returns 404")
    public ResponseEntity<Produto> findById(@PathVariable long id){
        Produto produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping()
    @ApiOperation("Returns a list of products")
    public ResponseEntity<List<Produto>> findAll(){
        return ResponseEntity.ok(produtoService.findAll());
    }

    @GetMapping("/")
    @ApiOperation("Returns a page of products according to the search")
    public ResponseEntity<Page<ProdutoDTO>> search(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Produto> produtosPage = produtoService.search(URL.decodeParam(nome), URL.decodeIntList(categorias), page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDTO  = produtosPage.map(produto -> new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco()));

        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/page")
    @ApiOperation("Returns a product page")
    public ResponseEntity<Page<Produto>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Produto> produtos = produtoService.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok(produtos);
    }

    @PostMapping("/")
    @ApiOperation("Save a product")
    public ResponseEntity<Void> save(@Valid @RequestBody Produto produto){
        Produto produtoSaved = produtoService.save(produto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produtoSaved.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a product")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Produto produto){
        produto.setId(id);
        produtoService.update(produto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a product")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
