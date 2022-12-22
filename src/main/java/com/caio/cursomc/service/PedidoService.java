package com.caio.cursomc.service;

import com.caio.cursomc.model.ItemPedido;
import com.caio.cursomc.model.PagamentoBoleto;
import com.caio.cursomc.model.Pedido;
import com.caio.cursomc.model.enums.TipoEstadoPagamento;
import com.caio.cursomc.repository.ItemPedidoRepository;
import com.caio.cursomc.repository.PagamentoRepository;
import com.caio.cursomc.repository.PedidoRepository;
import com.caio.cursomc.service.exceptions.DataIntegrityException;
import com.caio.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido findById(Long id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado ou não existe, ID:" + id, new Throwable("Tipo: " + Pedido.class.getName())));
    }

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido save(Pedido pedido){
        pedido.setId(null);
        pedido.setInstant(new Date());
        pedido.getPagamento().setEstadoPagamento(TipoEstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if(pedido.getPagamento() instanceof PagamentoBoleto){
            PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamentoBoleto, pedido.getInstant());
        }

        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for(ItemPedido itemPedido : pedido.getItens()){
            itemPedido.setDesconto(0.0);
            itemPedido.setPreco(produtoService.findById(itemPedido.getProduto().getId()).getPreco());
            itemPedido.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItens());

        return pedido;
    }

    public void delete(Long id){
        this.findById(id);
        try{
            pedidoRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Erro ao deletar, existem itens associados: " + id);
        }
    }
}
