package com.caio.cursomc;

import com.caio.cursomc.model.*;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.model.enums.TipoEstadoPagamento;
import com.caio.cursomc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Eletronicos");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Mouse", 50.0);
		Produto p2 = new Produto(null, "Computador", 5000.0);
		Produto p3 = new Produto(null, "Impressora", 500.0);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p1, p3));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat2));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "São paulo");
		Estado est2 = new Estado(null, "Bahia");
		Estado est3 = new Estado(null, "Santa Catarina");

		Cidade c1 = new Cidade(null, "São Paulo", est1);
		Cidade c2 = new Cidade(null, "Salvador", est2);
		Cidade c3 = new Cidade(null, "Florianopolis", est3);
		Cidade c4 = new Cidade(null, "Ubatuba", est1);
		Cidade c5 = new Cidade(null, "Rio preto", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c4 ,c5));
		est2.getCidades().addAll(Arrays.asList(c2));
		est3.getCidades().addAll(Arrays.asList(c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2, est3));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));

		Cliente cli1= new Cliente(null, "Jocimar", "jocima@gmail.com", "12232159301", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("20123132131", "11 938102121"));

		Endereco end1 = new Endereco(null, "Rua abc", "123", "nenhum", "Penteado", "21212021", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua do alemao", "321", "perto da arvore", "Vila marques", "11112021", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2012 12:23"), cli1, end1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/01/2015 10:40"), cli1, end2);

		Pagamento pagamentoCartao = new PagamentoCartao(null, TipoEstadoPagamento.QUITADO, pedido1, 2);
		pedido1.setPagamento(pagamentoCartao);

		Pagamento pagamentoBoleto = new PagamentoBoleto(null, TipoEstadoPagamento.PENDENTE, pedido2, sdf.parse("30/10/2012 00:00"), null);
		pedido2.setPagamento(pagamentoBoleto);

		cli1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamentoCartao, pagamentoBoleto));

		ItemPedido item1 = new ItemPedido(pedido1, p1, 12.2, 5, 2000.0);
		ItemPedido item2 = new ItemPedido(pedido2, p2, 10.0, 2, 500.0);
		ItemPedido item3 = new ItemPedido(pedido1, p3, 00.0, 1, 100.0);

		pedido1.getItens().addAll(Arrays.asList(item1, item3));
		pedido2.getItens().addAll(Arrays.asList(item2));

		p1.getItens().add(item1);
		p2.getItens().add(item2);
		p3.getItens().add(item3);

		itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));

	}
}
