package com.rajiv.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rajiv.cursomc.domain.Categoria;
import com.rajiv.cursomc.domain.Cidade;
import com.rajiv.cursomc.domain.Cliente;
import com.rajiv.cursomc.domain.Endereco;
import com.rajiv.cursomc.domain.Estado;
import com.rajiv.cursomc.domain.ItemPedido;
import com.rajiv.cursomc.domain.Pagamento;
import com.rajiv.cursomc.domain.PagamentoComBoleto;
import com.rajiv.cursomc.domain.PagamentoComCartao;
import com.rajiv.cursomc.domain.Pedido;
import com.rajiv.cursomc.domain.Produto;
import com.rajiv.cursomc.domain.enums.EstadoPagamento;
import com.rajiv.cursomc.domain.enums.TipoCliente;
import com.rajiv.cursomc.repositories.CategoriaRepository;
import com.rajiv.cursomc.repositories.CidadeRepository;
import com.rajiv.cursomc.repositories.ClienteRepository;
import com.rajiv.cursomc.repositories.EnderecoRepository;
import com.rajiv.cursomc.repositories.EstadoRepository;
import com.rajiv.cursomc.repositories.ItemPedidoRepository;
import com.rajiv.cursomc.repositories.PagamentoRepository;
import com.rajiv.cursomc.repositories.PedidoRepository;
import com.rajiv.cursomc.repositories.ProdutoRepository;

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
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
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
		
		// Categoria e Produtos
		Categoria catInformatica = new Categoria(null, "Informática");
		Categoria catEscritorio = new Categoria(null, "Escritório");
		
		Produto prodComputador = new Produto(null, "Computador", 2000.00);
		Produto prodImpressora = new Produto(null, "Impressora", 800.00);
		Produto prodMouse = new Produto(null, "Mouse", 40.00);
		
		catInformatica.getProdutos().addAll(Arrays.asList(prodComputador, prodImpressora, prodMouse));
		catEscritorio.getProdutos().addAll(Arrays.asList(prodImpressora));
		
		prodComputador.getCategorias().addAll(Arrays.asList(catInformatica));
		prodImpressora.getCategorias().addAll(Arrays.asList(catInformatica, catEscritorio));
		prodMouse.getCategorias().addAll(Arrays.asList(catInformatica));
		
		// Adicionando mais categorias pra testar paginação
		Categoria cat3 = new Categoria(null, "Cat3");
		Categoria cat4 = new Categoria(null, "Cat4");
		Categoria cat5 = new Categoria(null, "Cat5");
		Categoria cat6 = new Categoria(null, "Cat6");
		Categoria cat7 = new Categoria(null, "Cat7");
		Categoria cat8 = new Categoria(null, "Cat8");
				
		categoriaRepository.saveAll(Arrays.asList(catInformatica, catEscritorio, cat3, cat4, cat5, cat6, cat7, cat8));
		produtoRepository.saveAll(Arrays.asList(prodComputador, prodImpressora, prodMouse));
		
		
		
		// Cidade e Estado
		Estado estadoSP = new Estado(null, "São Paulo");
		Estado estadoMG = new Estado(null, "Minas Gerais");
		
		Cidade uberlandia = new Cidade(null, "Uberlândia", estadoMG);
		Cidade saoPaulo = new Cidade(null, "São Paulo", estadoSP);
		Cidade campinas = new Cidade(null, "Campinas", estadoSP);
		
		estadoSP.getCidades().addAll(Arrays.asList(saoPaulo, campinas));
		estadoMG.getCidades().addAll(Arrays.asList(uberlandia));
		
		estadoRepository.saveAll(Arrays.asList(estadoSP, estadoMG));
		cidadeRepository.saveAll(Arrays.asList(saoPaulo, campinas, uberlandia));
		
		// Cliente
		Cliente mariaSilva = new Cliente(null, "Maria Silva", "maria.silva@teste.com", "36378912377", TipoCliente.PESSOAFISICA);
		mariaSilva.getTelefones().addAll(Arrays.asList("123456789", "987654321"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", mariaSilva, uberlandia);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", mariaSilva, saoPaulo);
		
		mariaSilva.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(mariaSilva));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		// Pedidos e Pagamentos
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), mariaSilva, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), mariaSilva, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pgto2);
		
		mariaSilva.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
		// Itens de Pedido
		ItemPedido ip1 = new ItemPedido(ped1, prodComputador, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, prodMouse, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, prodImpressora, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		prodComputador.getItens().addAll(Arrays.asList(ip1));
		prodMouse.getItens().addAll(Arrays.asList(ip2));
		prodImpressora.getItens().addAll(Arrays.asList(ip3));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}