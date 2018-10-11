package com.rajiv.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rajiv.cursomc.domain.Categoria;
import com.rajiv.cursomc.domain.Cidade;
import com.rajiv.cursomc.domain.Estado;
import com.rajiv.cursomc.domain.Produto;
import com.rajiv.cursomc.repositories.CategoriaRepository;
import com.rajiv.cursomc.repositories.CidadeRepository;
import com.rajiv.cursomc.repositories.EstadoRepository;
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
		
		categoriaRepository.saveAll(Arrays.asList(catInformatica, catEscritorio));
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
	}
}