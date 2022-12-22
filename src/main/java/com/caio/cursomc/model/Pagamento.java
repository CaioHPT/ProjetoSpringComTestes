package com.caio.cursomc.model;

import com.caio.cursomc.model.enums.TipoEstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private Integer estadoPagamento;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    @JsonIgnore
    private Pedido pedido;

    public Pagamento(){

    }

    public Pagamento(Long id, TipoEstadoPagamento estadoPagamento, Pedido pedido) {
        this.id = id;
        this.estadoPagamento = estadoPagamento == null ? null : estadoPagamento.getCodigo();
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEstadoPagamento getEstadoPagamento() {
        return TipoEstadoPagamento.toEnum(estadoPagamento);
    }

    public void setEstadoPagamento(TipoEstadoPagamento estadoPagamento) {
        this.estadoPagamento = estadoPagamento.getCodigo();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", estadoPagamento=" + estadoPagamento +
                ", pedido=" + pedido +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
