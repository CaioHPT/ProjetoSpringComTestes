package com.caio.cursomc.service.validation;

import com.caio.cursomc.DTO.ClienteInsertDTO;
import com.caio.cursomc.controller.exceptions.FieldMessage;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.repository.ClienteRepository;
import com.caio.cursomc.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteInsertDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann){

    }

    @Override
    public boolean isValid(ClienteInsertDTO clienteInsertDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();

        if(clienteInsertDTO.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isValidCPF(clienteInsertDTO.getCpf_cnpj())){
            list.add(new FieldMessage("cpf_cnpj", "CPF invalido"));
        }

        if(clienteInsertDTO.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCNPJ(clienteInsertDTO.getCpf_cnpj())){
            list.add(new FieldMessage("cpf_cnpj", "CNPJ invalido"));
        }

        if(clienteRepository.findByEmail(clienteInsertDTO.getEmail()) != null){
            list.add(new FieldMessage("email", "Email ja utilizado"));
        }

        for(FieldMessage message : list){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message.getMessage()).addPropertyNode(message.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

}
