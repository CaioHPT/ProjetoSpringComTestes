package com.caio.cursomc.service;

import com.caio.cursomc.model.PagamentoBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {


    public void preencherPagamentoComBoleto(PagamentoBoleto pagamentoBoleto, Date instat){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instat);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        pagamentoBoleto.setDataVecimento(calendar.getTime());
    }
}
