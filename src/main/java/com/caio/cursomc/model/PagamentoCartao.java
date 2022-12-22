package com.caio.cursomc.model;

import com.caio.cursomc.model.enums.TipoEstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoCartao extends Pagamento {

    private Integer numeroDeParcelas;

    public PagamentoCartao(){

    }

    public PagamentoCartao(Long id, TipoEstadoPagamento estadoPagamento, Pedido pedido, Integer numeroDeParcelas) {
        super(id, estadoPagamento, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }

    @Override
    public String toString() {
        return "PagamentoCartao{" +
                "numeroDeParcelas=" + numeroDeParcelas +
                '}';
    }
}
