package com.caio.cursomc.controller;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.service.CidadeService;
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
@RequestMapping("/cidade")
@Api("City REST API")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping("/{id}")
    @ApiOperation("Returns city by id if found, otherwise returns 404")
    public ResponseEntity<Cidade> findById(@PathVariable long id){
        Cidade cidade = cidadeService.findById(id);
        return ResponseEntity.ok(cidade);
    }

    @GetMapping
    @ApiOperation("Returns a list of citys")
    public ResponseEntity<List<Cidade>> findAll(){
        return ResponseEntity.ok(cidadeService.findAll());
    }

    @GetMapping("/page")
    @ApiOperation("Returns a city page")
    public ResponseEntity<Page<Cidade>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Cidade> cidades = cidadeService.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok(cidades);
    }

    @PostMapping("/")
    @ApiOperation("Save a city")
    public ResponseEntity<Void> save(@Valid @RequestBody Cidade cidade){
        Cidade cidadeSaved = cidadeService.save(cidade);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cidadeSaved.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a city")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Cidade cidade){
        cidade.setId(id);
        cidadeService.update(cidade);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a city")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
