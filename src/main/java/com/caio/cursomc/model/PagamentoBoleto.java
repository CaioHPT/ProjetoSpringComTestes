package com.caio.cursomc.model;

import com.caio.cursomc.model.enums.TipoEstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoBoleto extends Pagamento {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataVecimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPagamento;

    public PagamentoBoleto(){

    }

    public PagamentoBoleto(Long id, TipoEstadoPagamento estadoPagamento, Pedido pedido, Date dataVecimento, Date dataPagamento) {
        super(id, estadoPagamento, pedido);
        this.dataVecimento = dataVecimento;
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVecimento() {
        return dataVecimento;
    }

    public void setDataVecimento(Date dataVecimento) {
        this.dataVecimento = dataVecimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Override
    public String toString() {
        return "PagamentoBoleto{" +
                "dataVecimento=" + dataVecimento +
                ", dataPagamento=" + dataPagamento +
                '}';
    }
}
