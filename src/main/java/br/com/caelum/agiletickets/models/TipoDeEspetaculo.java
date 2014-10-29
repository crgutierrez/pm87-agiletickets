package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {

	CINEMA(0.05, 0.10) {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaTaxaOcupacao(sessao, ocupacao(), taxaOcupacao());
		}
	},
	SHOW(0.05, 0.10) {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaTaxaOcupacao(sessao, ocupacao(), taxaOcupacao());
		}
	},
	TEATRO(0, 0) {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return sessao.getPreco();
		}
	},
	BALLET(0.50, 0.20) {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			BigDecimal preco = calculaTaxaOcupacao(sessao, ocupacao(), taxaOcupacao());

			preco = calculaTaxaDeDuracao(sessao, preco);
			return preco;
		}
	},
	ORQUESTRA(0.50, 0.20) {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			BigDecimal preco = calculaTaxaOcupacao(sessao, ocupacao(), taxaOcupacao());

			preco = calculaTaxaDeDuracao(sessao, preco);

			return preco;
		}
	};

	private double ocupacao;
	private double taxaOcupacao;

	private TipoDeEspetaculo(double ocupacao, double taxaOcupacao) {
		this.ocupacao = ocupacao;
		this.taxaOcupacao = taxaOcupacao;
	}

	public double ocupacao() {
		return ocupacao;
	}

	public double taxaOcupacao() {
		return taxaOcupacao;
	}

	private static BigDecimal calculaTaxaDeDuracao(Sessao sessao, BigDecimal preco) {
		if (sessao.getDuracaoEmMinutos() > 60) {
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		}
		return preco;
	}

	public abstract BigDecimal calculaPreco(Sessao sessao);

	private static BigDecimal calculaTaxaOcupacao(Sessao sessao, double ocupacao, double taxaOcupacao) {
		BigDecimal preco;
		if ((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= ocupacao) {
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(taxaOcupacao)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}

}
