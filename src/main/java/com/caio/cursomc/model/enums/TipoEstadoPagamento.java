package com.caio.cursomc.model.enums;

public enum TipoEstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private Integer codigo;
    private String descricao;

    private TipoEstadoPagamento(Integer codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }
    public String getDescricao() {
        return descricao;
    }

    public static TipoEstadoPagamento toEnum(Integer codigo){
        if (codigo == null){
            return null;
        }

        for(TipoEstadoPagamento tipoEstadoPagamento: TipoEstadoPagamento.values()){
            if(tipoEstadoPagamento.codigo == codigo){
                return tipoEstadoPagamento;
            }
        }

        throw new IllegalArgumentException("Codigo invalido: " + codigo);
    }
}
