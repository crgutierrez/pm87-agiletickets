package br.com.caelum.agiletickets.models;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;

public enum Periodicidade {
	
	DIARIA {

		@Override
		public List<Sessao> criaSessoes(LocalDate inicio, LocalDate fim) {
			
			List<Sessao> sessoes = new ArrayList<Sessao>();
			int dias = Days.daysBetween(inicio, fim).getDays();
			criaSessoesPorTamanhoPeriodo(sessoes , dias);
			return sessoes;
		}
		
	}, SEMANAL {

		@Override
		public List<Sessao> criaSessoes(LocalDate inicio, LocalDate fim) {
			List<Sessao> sessoes = new ArrayList<Sessao>();
			int semanas = Weeks.weeksBetween(inicio, fim).getWeeks();
			criaSessoesPorTamanhoPeriodo(sessoes, semanas);
			return sessoes;
		}
	
	};
	
	
	public abstract List<Sessao> criaSessoes(LocalDate inicio, LocalDate fim ); 
	
	private static void criaSessoesPorTamanhoPeriodo(List<Sessao> sessoes, int semanas) {
		for (int i = 0; i <= semanas; i++) {
			Sessao sessao = new Sessao();
			sessoes.add(sessao);
		}
	}
}
