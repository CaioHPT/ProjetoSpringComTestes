package com.caio.cursomc.controller;

import com.caio.cursomc.model.Estado;
import com.caio.cursomc.service.EstadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estado")
@Api("State REST API")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping("/{id}")
    @ApiOperation("Returns state by id if found, otherwise returns 404")
    public ResponseEntity<Estado> findById(@PathVariable long id){
        Estado estado = estadoService.findById(id);
        return ResponseEntity.ok(estado);
    }

    @GetMapping()
    @ApiOperation("Returns a list of states")
    public ResponseEntity<List<Estado>> findAll(){
        return ResponseEntity.ok(estadoService.findAll());
    }

    @GetMapping("/page")
    @ApiOperation("Returns a state page")
    public ResponseEntity<Page<Estado>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Estado> estados = estadoService.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok(estados);
    }

    @PostMapping("/")
    @ApiOperation("Save a state")
    public ResponseEntity<Void> save(@Valid @RequestBody Estado estado){
        Estado estadoSaved = estadoService.save(estado);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estadoSaved.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a state")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Estado estado){
        estado.setId(id);
        estadoService.update(estado);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a state")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        estadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
