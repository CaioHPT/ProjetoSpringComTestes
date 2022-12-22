package com.caio.cursomc.service.validation;

import com.caio.cursomc.DTO.ClienteDTO;
import com.caio.cursomc.DTO.ClienteInsertDTO;
import com.caio.cursomc.controller.exceptions.FieldMessage;
import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.repository.ClienteRepository;
import com.caio.cursomc.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(ClienteUpdate ann){

    }

    @Override
    public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext constraintValidatorContext) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long id = Long.parseLong(map.get("id"));
        List<FieldMessage> list = new ArrayList<>();

        Cliente retorno = clienteRepository.findByEmail(clienteDTO.getEmail());

        if(retorno != null && retorno.getId() != id){
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
