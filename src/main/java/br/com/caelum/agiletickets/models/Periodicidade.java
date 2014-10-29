package br.com.caelum.agiletickets.models;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;

public enum Periodicidade {

	DIARIA {

		@Override
		public List<Sessao> criaSessoes(Espetaculo espetaculo,LocalDate inicio, LocalDate fim, LocalTime horario) {
			List<Sessao> sessoes = new ArrayList<Sessao>();
			int dias = Days.daysBetween(inicio, fim).getDays();
			for (int i = 0; i <= dias; i++) {
				LocalDate inicioSessao = inicio.plusDays(i);
				Sessao sessao = new Sessao();
				sessao.setEspetaculo(espetaculo);
				sessao.setInicio(inicioSessao.toDateTime(horario));
				sessao.setTotalIngressos(10);
				sessoes.add(sessao);
			}
		
			return sessoes;
		}

	},
	SEMANAL {

		@Override
		public List<Sessao> criaSessoes(Espetaculo espetaculo,LocalDate inicio, LocalDate fim, LocalTime horario) {
			List<Sessao> sessoes = new ArrayList<Sessao>();
			int semanas = Weeks.weeksBetween(inicio, fim).getWeeks();
			for (int i = 0; i <= semanas; i++) {
				LocalDate inicioSessao = inicio.plusWeeks(i);
				
				Sessao sessao = new Sessao();
			
				sessao.setEspetaculo(espetaculo);
				sessao.setInicio(inicioSessao.toDateTime(horario));
				sessao.setTotalIngressos(10);
				sessoes.add(sessao);
			}
			
			return sessoes;
		}

	};

	public abstract List<Sessao> criaSessoes(Espetaculo espetaculo, LocalDate inicio, LocalDate fim, LocalTime horario);
	
}
