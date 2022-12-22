package com.caio.cursomc.controller;

import com.caio.cursomc.model.*;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.model.enums.TipoEstadoPagamento;
import com.caio.cursomc.service.EnderecoService;
import com.caio.cursomc.service.PedidoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class PedidoControllerTest {

    private Estado estado;
    private Cidade cidade;
    private Cliente cliente;
    private Pedido pedido;
    private Endereco endereco;
    private Pagamento pagamento;
    private ItemPedido itemPedido;
    private Produto produto;

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    private static final String NAME_STATE_CITY = "São paulo";
    private static final String NAME_CLIENT = "Jocimar";
    private static final String EMAIL_CLIENT = "jocimar@gmail.com";
    private static final String CPF_CLIENT = "12232159301";
    private static final String PUBLIC_PLACE = "Rua do mockito";
    private static final String NUMBER = "777";
    private static final String COMPLEMENT = "Bloco 1";
    private static final String DISTRICT = "Junit";
    private static final String CEP = "21212021";
    private static final String NAME_PRODUCT = "MOUSE";
    private static final Double PRICE_PRODUCT = 50.0;
    private static final Double DISCOUNT = 12.0;
    private static final Integer AMOUNT = 2;
    private static final Double ORDER_ITEM_PRICE = 200.0;

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Pedido> pedidos = Arrays.asList(pedido);

        BDDMockito.when(pedidoService.findAll()).thenReturn(pedidos);

        BDDMockito.when(pedidoService.findById(ArgumentMatchers.anyLong())).thenReturn(pedido);

        BDDMockito.when(pedidoService.save(ArgumentMatchers.any(Pedido.class))).thenReturn(pedido);

        BDDMockito.doNothing().when(pedidoService).delete(1L);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    @DisplayName("List return of pedidos when Sucessful")
    void list_ReturnPedidos_WhenSucessful(){
        ResponseEntity<List<Pedido>> pedidos = pedidoController.findAll();

        Assertions.assertThat(pedidos).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(pedidos.getClass());

        Assertions.assertThat(pedidos.getBody()).isNotNull();

        Assertions.assertThat(pedidos.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(pedidos.getBody().get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(pedidos.getBody().get(0).getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(pedidos.getBody().get(0).getTotal()).isNotNull();

        Assertions.assertThat(pedidos.getBody().get(0).getInstant()).isNotNull();

        Assertions.assertThat(pedidos.getBody().get(0).getEnderecoDeEntrega()).isNotNull().isEqualTo(endereco);

        Assertions.assertThat(pedidos.getBody().get(0).getItens()).isNotNull().isEqualTo(new HashSet(Arrays.asList(itemPedido)));

        Assertions.assertThat(pedidos.getBody().get(0).getPagamento()).isNotNull().isEqualTo(pagamento);

        Assertions.assertThat(pedidos.getBody().get(0).getClass()).isEqualTo(Pedido.class);

        Assertions.assertThat(pedidos.getBody().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(pedidoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Pedido>> pedidos = pedidoController.findAll();

        Assertions.assertThat(pedidos.getBody()).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(pedidos.getBody().isEmpty()).isTrue();

        Assertions.assertThat(pedidos).isNotNull();

        Assertions.assertThat(pedidos.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(ResponseEntity.class).isEqualTo(pedidos.getClass());

    }

    @Test
    @DisplayName("Return Pedido by id when Sucessful")
    void returnPedidoById_WhenSucessful(){
        ResponseEntity<Pedido> pedidoReturn = pedidoController.findById(1L);

        Assertions.assertThat(pedidoReturn).isNotNull();

        Assertions.assertThat(pedidoReturn.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(pedidoReturn.getBody()).isNotNull();

        Assertions.assertThat(pedidoReturn.getBody().getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(pedidoReturn.getBody().getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(pedidoReturn.getBody().getTotal()).isNotNull();

        Assertions.assertThat(pedidoReturn.getBody().getInstant()).isNotNull();

        Assertions.assertThat(pedidoReturn.getBody().getEnderecoDeEntrega()).isNotNull().isEqualTo(endereco);

        Assertions.assertThat(pedidoReturn.getBody().getItens()).isNotNull().isEqualTo(new HashSet(Arrays.asList(itemPedido)));

        Assertions.assertThat(pedidoReturn.getBody().getPagamento()).isNotNull().isEqualTo(pagamento);

        Assertions.assertThat(Pedido.class).isEqualTo(pedidoReturn.getBody().getClass());

        Assertions.assertThat(ResponseEntity.class).isEqualTo(pedidoReturn.getClass());

    }

    @Test
    @DisplayName("Save Pedido when sucessful")
    void save_AddPedido_WhenSucessful(){
        ResponseEntity<Void> retorno = pedidoController.save(new Pedido(1L, new Date(), cliente, endereco));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getHeaders().get("Location")).isNotNull().isNotEmpty();

    }

    @Test
    @DisplayName("Delete Pedido when sucessful")
    void delete_RemovePedido_WhenSucessful(){
        ResponseEntity<Void> retorno = pedidoController.delete(1L);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(retorno).isNotNull();
    }

    private void startEntitys(){
        estado = new Estado(1L, NAME_STATE_CITY);
        cidade = new Cidade(1L, NAME_STATE_CITY, estado);
        cliente= new Cliente(1L, NAME_CLIENT, EMAIL_CLIENT, CPF_CLIENT, TipoCliente.PESSOA_FISICA);
        endereco = new Endereco(1L, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade);
        pedido = new Pedido(1L, new Date(), cliente, endereco);
        pagamento = new PagamentoCartao(null, TipoEstadoPagamento.QUITADO, pedido, 2);
        pedido.setPagamento(pagamento);
        produto = new Produto(1L, NAME_PRODUCT, PRICE_PRODUCT);
        itemPedido = new ItemPedido(pedido, produto, DISCOUNT, AMOUNT, ORDER_ITEM_PRICE);

        pedido.getItens().add(itemPedido);

    }
}
