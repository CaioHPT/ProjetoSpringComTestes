package com.caio.cursomc.controller;

import com.caio.cursomc.DTO.CategoriaDTO;
import com.caio.cursomc.model.Categoria;
import com.caio.cursomc.service.CategoriaService;
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
@RequestMapping("/categoria")
@Api("Category REST API")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{id}")
    @ApiOperation("Returns category by id if found, otherwise returns 404")
    public ResponseEntity<Categoria> findById(@PathVariable long id){
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping()
    @ApiOperation("Returns a list of categories")
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/page")
    @ApiOperation("Returns a category page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Categoria> categoriaPage = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listDTO  = categoriaPage.map(categoria -> new CategoriaDTO(categoria.getId(), categoria.getNome()));

        return ResponseEntity.ok(listDTO);
    }

    @PostMapping("/")
    @ApiOperation("Save a category")
    public ResponseEntity<Void> save(@Valid @RequestBody CategoriaDTO categoriaDto){
        Categoria categoria = categoriaService.save(categoriaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a category")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoria){
        categoria.setId(id);
        categoriaService.update(categoria);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a category")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
