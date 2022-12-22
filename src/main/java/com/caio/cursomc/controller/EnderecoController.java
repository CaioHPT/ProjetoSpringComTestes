package com.caio.cursomc.controller;

import com.caio.cursomc.model.Endereco;
import com.caio.cursomc.service.EnderecoService;
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
@RequestMapping("/endereco")
@Api("Address REST API")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{id}")
    @ApiOperation("Returns address by id if found, otherwise returns 404")
    public ResponseEntity<Endereco> findById(@PathVariable long id){
        Endereco endereco = enderecoService.findById(id);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping
    @ApiOperation("Returns a list of address")
    public ResponseEntity<List<Endereco>> findAll(){
        return ResponseEntity.ok(enderecoService.findAll());
    }

    @GetMapping("/page")
    @ApiOperation("Returns a address page")
    public ResponseEntity<Page<Endereco>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Endereco> enderecos = enderecoService.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok(enderecos);
    }

    @PostMapping("/")
    @ApiOperation("Save a address")
    public ResponseEntity<Void> save(@Valid @RequestBody Endereco endereco){
        Endereco enderecoSaved = enderecoService.save(endereco);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(enderecoSaved.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a address")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Endereco endereco){
        endereco.setId(id);
        enderecoService.update(endereco);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a address")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
