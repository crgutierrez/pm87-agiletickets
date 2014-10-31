package br.com.caelum.agiletickets.models;

public class EspetaculoBuilder {
	private Espetaculo espetaculo;

	public EspetaculoBuilder() {
		this.espetaculo = new Espetaculo();
	}

	public EspetaculoBuilder chamado(String nome) {
		espetaculo.setNome(nome);
		return this;
	}

	public EspetaculoBuilder queEh(String descricao) {
		espetaculo.setDescricao(descricao);

		return this;
	}

	public EspetaculoBuilder doTipo(TipoDeEspetaculo tipo) {
		espetaculo.setTipo(tipo);
		return this;
	}

	public Espetaculo cria() {
		return espetaculo;
	}

}
