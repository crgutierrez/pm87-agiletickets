package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {

	CINEMA {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaPrecoCinemaShow(sessao);
		}
	},
	SHOW {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaPrecoCinemaShow(sessao);
		}
	},
	TEATRO {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return sessao.getPreco();
		}
	},
	BALLET {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaPrecoBalletOrquestra(sessao);
		}
	},
	ORQUESTRA {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaPrecoBalletOrquestra(sessao);
		}
	};

	public abstract BigDecimal calculaPreco(Sessao sessao);

	private static BigDecimal calculaPrecoBalletOrquestra(Sessao sessao) {
		BigDecimal preco;
		double ocupacao = 0.50;
		double taxaOcupacao = 0.20;
		preco = calculaTaxaOcupacao(sessao, ocupacao, taxaOcupacao);

		if (sessao.getDuracaoEmMinutos() > 60) {
			preco = preco.add(sessao.getPreco().multiply(
					BigDecimal.valueOf(0.10)));
		}
		return preco;
	}

	private static BigDecimal calculaTaxaOcupacao(Sessao sessao, double ocupacao,
			double taxaOcupacao) {
		BigDecimal preco;
		if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue() <= ocupacao) {
			preco = sessao.getPreco().add(
					sessao.getPreco().multiply(BigDecimal.valueOf(taxaOcupacao)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}

	private static BigDecimal calculaPrecoCinemaShow(Sessao sessao) {
		BigDecimal preco;
		double ocupacao = 0.05;
		double taxaOcupacao = 0.10;
		preco = calculaTaxaOcupacao(sessao, ocupacao, taxaOcupacao);
		
		return preco;
	}
}
