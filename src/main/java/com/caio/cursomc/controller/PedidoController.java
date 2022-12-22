package com.caio.cursomc.controller;

import com.caio.cursomc.model.Pedido;
import com.caio.cursomc.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Api("Request REST API")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    @ApiOperation("Returns request by id if found, otherwise returns 404")
    public ResponseEntity<Pedido> findById(@PathVariable long id){
        Pedido pedido = pedidoService.findById(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping()
    @ApiOperation("Returns a list of request")
    public ResponseEntity<List<Pedido>> findAll(){
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @PostMapping("/")
    @ApiOperation("Save a request")
    public ResponseEntity<Void> save(@Valid @RequestBody Pedido pedido){
        Pedido pedidoSaved = pedidoService.save(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedidoSaved.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a request")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
