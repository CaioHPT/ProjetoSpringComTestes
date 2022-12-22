package com.caio.cursomc.DTO;

import com.caio.cursomc.service.validation.ClienteInsert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ClienteInsert
public class ClienteInsertDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
    @Length(min = 3, max = 50, message = "O tamanho do campo deve ser entre 3 e 50 caracteres")
    private String nome;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
    @Email(message = "email invalido")
    private String email;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
    private String cpf_cnpj;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    private Integer tipo;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
    @Length(min = 5, max = 50, message = "O tamanho do campo deve ser entre 5 e 50 caracteres")
    private String logradouro;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
    @Length(min = 1, max = 10, message = "O tamanho do campo deve ser entre 1 e 10  caracteres")
    private String numero;

    private String complemento;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
    @Length(min = 5, max = 50, message = "O tamanho do campo deve ser entre 5 e 50 caracteres")
    private String bairro;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
    @Length(min = 8, max = 8, message = "O tamanho do campo deve de 8 caracteres")
    private String cep;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
    private List<String> telefones;

    @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
    private Long cidadeId;

    public ClienteInsertDTO() {
    }

    public ClienteInsertDTO(@NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                            @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
                            @Length(min = 3, max = 50, message = "O tamanho do campo deve ser entre 3 e 50 caracteres")
                                    String nome,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                            @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
                            @Email(message = "email invalido")
                                    String email,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                            @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
                                    String cpf_cnpj,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                                    Integer tipo,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                            @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
                            @Length(min = 5, max = 50, message = "O tamanho do campo deve ser entre 5 e 50 caracteres")
                                    String logradouro,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                            @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
                            @Length(min = 1, max = 10, message = "O tamanho do campo deve ser entre 1 e 10  caracteres")
                                    String numero,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                            @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
                            @Length(min = 5, max = 50, message = "O tamanho do campo deve ser entre 5 e 50 caracteres")
                                    String bairro,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                            @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
                            @Length(min = 8, max = 8, message = "O tamanho do campo deve de 8 caracteres")
                                    String cep,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                            @NotEmpty(message = "Campo nao pode ser vazio, campo obrigatorio")
                                    List<String> telefones,
                            @NotNull(message = "Campo nao pode ser nulo, campo obrigatorio")
                                    Long cidadeId) {
        this.nome = nome;
        this.email = email;
        this.cpf_cnpj = cpf_cnpj;
        this.tipo = tipo;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.telefones = telefones;
        this.cidadeId = cidadeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Long cidadeId) {
        this.cidadeId = cidadeId;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }
}
