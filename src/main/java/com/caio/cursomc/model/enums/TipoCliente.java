package com.caio.cursomc.model.enums;

public enum TipoCliente {

    PESSOA_FISICA(1, "Pessoa fisica"),
    PESSOA_JURIDICA(2, "Pessoa Juridica");

    private Integer codigo;
    private String descricao;

    private TipoCliente(Integer codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }
    public String getDescricao() {
        return descricao;
    }

    public static TipoCliente toEnum(Integer codigo){
        if (codigo == null){
            return null;
        }

        for(TipoCliente tipoCliente: TipoCliente.values()){
            if(tipoCliente.codigo == codigo){
                return tipoCliente;
            }
        }

        throw new IllegalArgumentException("Codigo invalido: " + codigo);
    }
}
