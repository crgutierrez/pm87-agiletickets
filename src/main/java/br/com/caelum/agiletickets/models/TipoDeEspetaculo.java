package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {

	CINEMA {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			double ocupacao = 0.05;
			double taxaOcupacao = 0.10;
			return calculaTaxaOcupacao(sessao, ocupacao, taxaOcupacao);
		}
	},
	SHOW {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			double ocupacao = 0.05;
			double taxaOcupacao = 0.10;
			return calculaTaxaOcupacao(sessao, ocupacao, taxaOcupacao);
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
			double ocupacao = 0.50;
			double taxaOcupacao = 0.20;
			BigDecimal preco = calculaTaxaOcupacao(sessao, ocupacao, taxaOcupacao);

			preco = calculaTaxaDeDuracao(sessao, preco);
			return preco;
		}
	},
	ORQUESTRA {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			double ocupacao = 0.50;
			double taxaOcupacao = 0.20;
			BigDecimal preco = calculaTaxaOcupacao(sessao, ocupacao, taxaOcupacao);

			preco = calculaTaxaDeDuracao(sessao, preco);
			
			return preco;
		}
	};

	
	private static  BigDecimal calculaTaxaDeDuracao(Sessao sessao, BigDecimal preco) {
		if (sessao.getDuracaoEmMinutos() > 60) {
			preco = preco.add(sessao.getPreco().multiply(
					BigDecimal.valueOf(0.10)));
		}
		return preco;
	}
	
	public abstract BigDecimal calculaPreco(Sessao sessao);

	
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

	
}
