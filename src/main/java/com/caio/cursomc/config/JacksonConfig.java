package com.caio.cursomc.config;

import com.caio.cursomc.model.PagamentoBoleto;
import com.caio.cursomc.model.PagamentoCartao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder(){
            public void configure(ObjectMapper objectMapper){
                objectMapper.registerSubtypes(PagamentoCartao.class);
                objectMapper.registerSubtypes(PagamentoBoleto.class);
                super.configure(objectMapper);
            }
        };

        return builder;
    }

}
