package com.caio.cursomc.controller;

import com.caio.cursomc.DTO.ClienteDTO;
import com.caio.cursomc.DTO.ClienteInsertDTO;
import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.service.ClienteService;
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
@RequestMapping("/cliente")
@Api("Client REST API")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    @ApiOperation("Returns client by id if found, otherwise returns 404")
    public ResponseEntity<Cliente> findById(@PathVariable long id){
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping()
    @ApiOperation("Returns a list of clients")
    public ResponseEntity<List<ClienteDTO>> findAll(){
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/page")
    @ApiOperation("Returns a client page")
    public ResponseEntity<Page<Cliente>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Cliente> clientes = clienteService.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/")
    @ApiOperation("Save a client")
    public ResponseEntity<Void> save(@Valid @RequestBody ClienteInsertDTO categoriaDto){
        Cliente cliente = clienteService.save(categoriaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a client")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO){
        clienteDTO.setId(id);
        clienteService.update(clienteDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a client")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
