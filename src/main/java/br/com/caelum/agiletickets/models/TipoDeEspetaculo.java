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
		if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue() <= 0.50) {
			preco = sessao.getPreco().add(
					sessao.getPreco().multiply(BigDecimal.valueOf(0.20)));
		} else {
			preco = sessao.getPreco();
		}

		if (sessao.getDuracaoEmMinutos() > 60) {
			preco = preco.add(sessao.getPreco().multiply(
					BigDecimal.valueOf(0.10)));
		}
		return preco;
	}

	private static BigDecimal calculaPrecoCinemaShow(Sessao sessao) {
		BigDecimal preco;
		if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue() <= 0.05) {
			preco = sessao.getPreco().add(
					sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}
}
