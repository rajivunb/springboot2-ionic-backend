package com.rajiv.cursomc.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int id;
	private String descricao;
	
	private EstadoPagamento(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public int getId() {
		return id;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		
		for (EstadoPagamento tipo : EstadoPagamento.values()) {
			if (id.equals(tipo.getId())) {
				return tipo;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + id);
	}
}
