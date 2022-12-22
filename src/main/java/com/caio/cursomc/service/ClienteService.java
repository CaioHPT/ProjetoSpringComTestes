package com.caio.cursomc.service;

import com.caio.cursomc.DTO.ClienteDTO;
import com.caio.cursomc.DTO.ClienteInsertDTO;
import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.model.Endereco;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.repository.ClienteRepository;
import com.caio.cursomc.repository.EnderecoRepository;
import com.caio.cursomc.service.exceptions.DataIntegrityException;
import com.caio.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente findById(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado ou não existe, ID:" + id, new Throwable("Tipo: " + Cliente.class.getName())));
    }

    public List<ClienteDTO> findAll(){
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTOS = clientes
                .stream()
                .map(cliente -> new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail()))
                .collect(Collectors.toList());
        return clientesDTOS;
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente save(ClienteInsertDTO clienteDTO) {
        Cliente cliente = this.fromDTO(clienteDTO);
        cliente.setId(null);
        Cliente clienteSaved = clienteRepository.save(cliente);
        Endereco endereco = enderecoRepository.save(cliente.getEnderecos().get(0));
        clienteSaved.setEnderecos(Arrays.asList(endereco));
        clienteSaved.setTelefones(cliente.getTelefones());
        return clienteSaved;
    }

    public Cliente update(ClienteDTO clienteDTO){
        this.findById(clienteDTO.getId());
        return clienteRepository.save(this.fromDTO(clienteDTO));
    }

    public void delete(Long id){
        this.findById(id);
        try{
            clienteRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Erro ao deletar, existem PEDIDOS associados a este cliente, Id: " + id);
        }
    }

    private Cliente fromDTO(ClienteDTO clienteDTO){
        Cliente cliente = this.findById(clienteDTO.getId());
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), cliente.getCpf_cnpj(), cliente.getTipoCliente());
    }

    private Cliente fromDTO(ClienteInsertDTO clienteDTO){
        Cliente cliente = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpf_cnpj(), TipoCliente.toEnum(clienteDTO.getTipo()));
        Cidade cidade = new Cidade(clienteDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);

        if(!clienteDTO.getTelefones().isEmpty() && clienteDTO.getTelefones() != null){
            for(String telefone: clienteDTO.getTelefones()){
                if(telefone != null || !telefone.isEmpty()){
                    cliente.getTelefones().add(telefone);
                }
            }
        }

        return cliente;
    }
}
