package com.caio.cursomc.DTO;

import com.caio.cursomc.service.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min = 3, max = 50, message = "O tamanho do campo deve ser entre 3 e 50 caracteres")
    @NotNull(message = "O campo nao pode ser nulo")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    public ClienteDTO(){

    }

    public ClienteDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
